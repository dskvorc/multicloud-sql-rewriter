import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlDialect;

public class MulticloudConvention extends JdbcConvention {

	private SchemaPlus rootSchema;

	private MulticloudConvention(SchemaPlus parentSchema, SqlDialect dialect, Expression expression, String name) {
		super(dialect, expression, name);
		rootSchema = parentSchema;
	}


	public static MulticloudConvention of(SchemaPlus parentSchema, SqlDialect dialect, Expression expression, String name) {
		return new MulticloudConvention(parentSchema, dialect, expression, name);
	}


	public void register(RelOptPlanner planner) {
		super.register(planner);
	}


	public SchemaPlus getRootSchema() {
		return rootSchema;
	}
}
