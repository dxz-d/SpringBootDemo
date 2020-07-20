import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class ActivityTest {
    @Test
    public void deployment(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService对象进行流程部署
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //进行部署,将对应的流程定义文件生成到数据库当中，作为记录进行保存
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("com/example/activity/demo.xml")     //加载流程文件
                .addClasspathResource("bpmn/demo.png")
                .name("请假流程")       //设置流程名称D:\java\idea\demo\src\main\java\com\example\activity\demo.xml
                .key("demoKey")
                .deploy();              //部署

        //输出部署信息
        System.out.println("流程名称："+deployment.getName());
        System.out.println("流程ID："+deployment.getId());
        System.out.println("流程Key："+deployment.getKey());
    }
}
