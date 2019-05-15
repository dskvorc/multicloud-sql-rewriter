import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.util.Pair;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;

public class MulticloudFragmentSchema extends JdbcSchema {

	public static HashMap<Pair<Connection, String>, MulticloudFragmentSchema> fragmentSchemas = new HashMap<>();


	public MulticloudFragmentSchema(SchemaPlus parentSchema, String name, DataSource dataSource, SqlDialect dialect, JdbcConvention convention, String catalog, String schema) {
		super(dataSource, dialect, convention, catalog, schema);

		Connection dbConnection = null;
		try {
			dbConnection = dataSource.getConnection();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}

		fragmentSchemas.put(new Pair<>(dbConnection, name), this);
	}
}
