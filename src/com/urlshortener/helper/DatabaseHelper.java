package com.urlshortener.helper;

import com.google.inject.Inject;
import com.urlshortener.constants.DatabaseConstants.ShortURLTable;
import com.urlshortener.constants.DatabaseConstants.CountersTable;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor(onConstructor_ = {@Inject})
public class DatabaseHelper {
    private final DataSource dataSource;

    public long getAndIncrement(final int id) throws SQLException {
        try (final Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    final PreparedStatement selectStmt = conn.prepareStatement(String.format(
                            "SELECT %s, %s FROM %s WHERE %s = ? FOR UPDATE",
                                    CountersTable.FIELD_CURRENT_VALUE,
                                    CountersTable.FIELD_END_VALUE,
                                    CountersTable.TABLE_NAME,
                                    CountersTable.FIELD_ID
                            )
                    );
                    final PreparedStatement updateStmt = conn.prepareStatement(String.format(
                                    "UPDATE %s SET %s = ? WHERE %s = ?",
                                    CountersTable.TABLE_NAME,
                                    CountersTable.FIELD_CURRENT_VALUE,
                                    CountersTable.FIELD_ID
                            )
                    )
            ) {
                // Lock the row
                selectStmt.setInt(1, id);
                final ResultSet rs = selectStmt.executeQuery();
                rs.next();

                final long current = rs.getLong(CountersTable.FIELD_CURRENT_VALUE);
                final long nextValue = current + 1;

                updateStmt.setLong(1, nextValue);
                updateStmt.setInt(2, id);
                updateStmt.executeUpdate();

                conn.commit();
                return current;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void saveShortenURL(final String url, final String shortURL) {
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(String.format(
                     "INSERT INTO %s (original_url, short_url) VALUES (?, ?)",
                     ShortURLTable.TABLE_NAME
             ))) {

            stmt.setString(1, url);
            stmt.setString(2, shortURL);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save shortened URL", e);
        }
    }

    public String getOriginalUrl(final String shortURL) {
        try (Connection conn = dataSource.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(String.format(
                     "SELECT original_url FROM %s WHERE short_url = ?",
                     ShortURLTable.TABLE_NAME
             ))) {

            stmt.setString(1, shortURL);
            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(ShortURLTable.FIELD_ORIGINAL_URL);
            } else {
                throw new RuntimeException("Short URL not found: " + shortURL);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get original URL", e);
        }
    }
}
