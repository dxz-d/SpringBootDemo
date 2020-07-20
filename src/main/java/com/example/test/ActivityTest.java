package com.example.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * spring整合activiti
 * @author 12057
 */
public class ActivityTest {
    /**
     1.流程定义
     */
    public static void main(String[] args) {
        //加载配置
        //ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //获取ProcessEngine对象
        //ProcessEngine processEngine = configuration.buildProcessEngine();

        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        // 引擎配置
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        // com.mysql.jdbc.Driver
        pec.setJdbcDriver("com.mysql.jdbc.Driver");
        // jdbc:mysql://localhost:3306/testdatabase
        pec.setJdbcUrl("jdbc:mysql://localhost:3306/activiti-y2170?serverTimezone=UTC");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("123456");

        /**
         * false 不能自动创建表
         * create-drop 先删除表再创建表
         * true 自动创建和更新表
         */
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();

    }

    /**
     * 2.流程部署
     */
    @Test
    public void deployment(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取RepositoryService对象进行流程部署
        //RepositoryService：activiti的资源管理类
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //进行部署,将对应的流程定义文件生成到数据库当中，作为记录进行保存
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/demo.bpmn")
                //加载流程文件
                .addClasspathResource("bpmn/demo.png")
                //设置流程名称D:\java\idea\demo\src\main\java\com\example\activity\demo.xml
                .name("请假流程")
                .key("demoKey")
                .deploy();              //部署

        //输出部署信息
        System.out.println("流程名称："+deployment.getName());
        System.out.println("流程ID："+deployment.getId());
        System.out.println("流程Key："+deployment.getKey());
    }

    /**
     * 3.创建流程实例
     */
    @Test
    public void startInstance(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取到RunimeService对象
        RuntimeService runtimeService=processEngine.getRuntimeService() ;
        //创建流程实例
        //startProcessInstanceByKey中的key为act_re_procdef表中的KEY_字段中的值
        ProcessInstance demo=runtimeService.startProcessInstanceByKey("myProcess_1");
        //输出实例信息
        System.out.println("流程部署ID："+demo.getDeploymentId());
        System.out.println("流程实例ID："+demo.getId());
        System.out.println("活动 ID:"+demo.getActivityId());
    }

    /**
     * 4.用户查询代办任务
     */
    //查看代办任务
    @Test
    public void getTask(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个TaskService对象
        TaskService taskService=processEngine.getTaskService();
        //查询代办业务 createTaskQuery查询任务   taskCandidateOrAssigned查询任务执行者   processDefinitionKey：查询流程
        /**
         * taskCandidateOrAssigned匹配规则:1.Assigned   2.配置bpmn文件中定义的值
         * taskAssignee匹配规则:1.Assigned
         */
        List<Task> list=taskService.createTaskQuery().taskCandidateOrAssigned("lisi").processDefinitionKey("myProcess_1").list();
        for (Task task:list){
            System.out.println("任务名称："+task.getName());
            System.out.println("任务执行人："+task.getAssignee());
            System.out.println("任务ID:"+task.getId());
            System.out.println("流程实例ID："+task.getProcessInstanceId());
        }
    }

    /**
     * 5.用户进行任务处理
     */
    @Test
    public void completeTask(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取一个TaskService对象
        TaskService taskService=processEngine.getTaskService();
        //任务处理   7505 15005
        taskService.complete("7505");
    }

    /**
     * 6.在查询zhangsan代办任务时就已经没有了
     */

    /**
     * 7.当业务流程结束后通过历史可以查看到已经走完的流程
     */
    @Test
    public void getHistory(){
        //获取ProcessEngine对象   默认配置文件名称：activiti.cfg.xml  并且configuration的Bean实例ID为processEngineConfiguration
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取HistoryService接口
        HistoryService historyService = processEngine.getHistoryService();
        //获取历史任务
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //获取指定流程实例的任务
        historicActivityInstanceQuery.processInstanceId("15005");
        //获取任务列表
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
        for (HistoricActivityInstance ai : list) {
            System.out.println("任务节点ID："+ai.getActivityId());
            System.out.println("任务节点名称："+ai.getActivityName());
            System.out.println("流程实例ID信息："+ai.getProcessDefinitionId());
            System.out.println("流程实例ID信息："+ai.getProcessInstanceId());
            System.out.println("==============================");
        }
    }
}

