import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlDialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


	public SchemaPlus getOriginSchema() {
		for (String subSchemaName : rootSchema.getSubSchemaNames()) {
			SchemaPlus subSchema = rootSchema.getSubSchema(subSchemaName);
			try {
				MulticloudOriginSchema originSchema = subSchema.unwrap(MulticloudOriginSchema.class);
				return subSchema;
			}
			catch (Exception e) {
				continue;
			}
		}
		return null;
	}


	public List<SchemaPlus> getFragmentSchemas() {
		ArrayList<SchemaPlus> fragmentSchemas = new ArrayList<>();
		Set<String> subSchemaNames = rootSchema.getSubSchemaNames();
		for (String name : subSchemaNames) {
			SchemaPlus subSchema = rootSchema.getSubSchema(name);
			try {
				MulticloudFragmentSchema fragmentSchema = subSchema.unwrap(MulticloudFragmentSchema.class);
				fragmentSchemas.add(subSchema);
			} catch (Exception e) {
				continue;
			}
		}
		return fragmentSchemas;
	}
}
