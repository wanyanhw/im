package com.wanyan.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    private final static String AUTHOR = "wanyanhw";

    private final static String MODULE_NAME = "core";
    private final static String PARENT_PAGE = "com.wanyan";
    private final static String ENTITY_PACKAGE = "entity";
    private final static String DAO_PACKAGE = "dao";
    private final static String DAO_IMPL_PACKAGE = "dao.impl";
    private final static String MAPPER_PACKAGE = "mapper";


    private final static String ENTITY_SUFFIX = "Entity";
    private final static String MAPPER_SUFFIX = "Mapper";
    private final static String DAO_PREFIX= "I";
    private final static String DAO_SUFFIX = "Dao";
    private final static String DAO_IMPL_SUFFIX = "DaoImpl";

    private final static String[] TABLES = {
            "message",
            "user",
            "user_detail"
    };

    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/im?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static void main(String[] args) {

        // 全局配置
//        String projectPath = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig()
                .setOutputDir(MODULE_NAME + "/src/main/java")
                .setAuthor(AUTHOR)
                .setOpen(false)
                .setEntityName("%s" + ENTITY_SUFFIX)
                .setMapperName("%s" + MAPPER_SUFFIX)
                .setXmlName("%s" + MAPPER_SUFFIX)
                .setServiceName(DAO_PREFIX + "%s" + DAO_SUFFIX)
                .setServiceImplName("%s" + DAO_IMPL_SUFFIX)
                .setBaseColumnList(true)
                .setBaseResultMap(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setUrl(jdbcUrl)
                // dsc.setSchemaName("public");
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("root");

        // 包配置
        PackageConfig pc = new PackageConfig()
                .setModuleName(MODULE_NAME)
                .setParent(PARENT_PAGE)
                .setEntity(ENTITY_PACKAGE).setMapper(MAPPER_PACKAGE)
                .setService(DAO_PACKAGE)
                .setServiceImpl(DAO_IMPL_PACKAGE);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return MODULE_NAME + "/src/main/resources/mapping/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setController(null)
                .setXml(null);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setEntityTableFieldAnnotationEnable(true)
                .setRestControllerStyle(true)
                .setInclude(TABLES)
                .setControllerMappingHyphenStyle(true)
                .setTablePrefix("");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator()
                .setTemplate(templateConfig)
                .setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setCfg(cfg)
                .setTemplate(templateConfig)
                .setStrategy(strategy);
        mpg.execute();
    }

}
