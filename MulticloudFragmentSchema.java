import org.apache.calcite.adapter.jdbc.JdbcConvention;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlDialect;

import javax.sql.DataSource;

public class MulticloudFragmentSchema extends JdbcSchema {

	public MulticloudFragmentSchema(SchemaPlus parentSchema,
									String name,
									DataSource dataSource,
									SqlDialect dialect,
									JdbcConvention convention,
									String catalog,
									String schema) {
		super(dataSource, dialect, convention, catalog, schema);
        //Hook.PROGRAM.add(MulticloudRewriteProgram.prepend(MulticloudRewriteProgram.INSTANCE));
	}
}
