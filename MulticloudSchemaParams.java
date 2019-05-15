import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Schemas;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.SqlDialectFactoryImpl;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.util.Map;

public class MulticloudSchemaParams {

	SchemaPlus parentSchema;
	String name;

	DataSource dataSource;
	SqlDialect dialect;
	JdbcConvention convention;
	String catalog;
	String schema;


	private MulticloudSchemaParams(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		try {
			String jdbcUrl = (String) operand.get("jdbcUrl");
			String jdbcDriver = (String) operand.get("jdbcDriver");
			String jdbcUser = (String) operand.get("jdbcUser");
			String jdbcPassword = (String) operand.get("jdbcPassword");

			dataSource = JdbcSchema.dataSource(jdbcUrl, jdbcDriver, jdbcUser, jdbcPassword);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Error while reading dataSource", e);
		}

		DatabaseMetaData dbMetaData;
		try {
			dbMetaData = dataSource.getConnection().getMetaData();
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Error while reading dataSource", e);
		}
		dialect = SqlDialectFactoryImpl.INSTANCE.create(dbMetaData);

		Expression expression = null;
		if (parentSchema != null) {
			expression = Schemas.subSchemaExpression(parentSchema, name, JdbcSchema.class);
		}
		convention = MulticloudConvention.of(parentSchema, dialect, expression, name);

		try {
			catalog = (String) operand.get("jdbcCatalog");
		}
		catch (Exception e) {
			// optional parameter
			catalog = null;
		}

		try {
			schema = (String) operand.get("jdbcSchema");
		}
		catch (Exception e) {
			// optional parameter
			schema = null;
		}

		this.parentSchema = parentSchema;
		this.name = name;
	}


	public static final MulticloudSchemaParams init(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		return new MulticloudSchemaParams(parentSchema, name, operand);
	}


	public SchemaPlus getParentSchema() {
		return parentSchema;
	}


	public String getName() {
		return name;
	}


	public DataSource getDataSource() {
		return dataSource;
	}


	public SqlDialect getDialect() {
		return dialect;
	}


	public JdbcConvention getConvention() {
		return convention;
	}


	public String getCatalog() {
		return catalog;
	}


	public String getSchema() {
		return schema;
	}
}
