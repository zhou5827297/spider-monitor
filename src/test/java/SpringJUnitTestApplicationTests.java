//import com.zhoukai.Application;
//import com.zhoukai.dubbo.ProcessService;
//import com.zhoukai.dubbo.SiteService;
//import com.zhoukai.entity.Process;
//import com.zhoukai.entity.Site;
//import com.zhoukai.vo.Response;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Date;
//import java.util.List;
//
////这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
//@RunWith(SpringJUnit4ClassRunner.class)
////这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//public class SpringJUnitTestApplicationTests {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ProcessService processService;
//
//    @Autowired
//    private SiteService siteService;
//
//    @Before
//    public void setupMockMvc() throws Exception {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//
//    @Test
//    public void testSite() throws Exception {
//        Site site = new Site();
//        site.setId("2");
//        site.setContext(" 三秦\n" +
//                "    近日，中共中央办公厅印发《意见》和《通知》，要求各级党组织推进“两学一做”学习教育常态化制度化，这对于进一步用习近平总书记系列重要讲话精神武装全党，确保全党更加紧密地团结在以习近平同志为核心的党中央周围，不断开创中国特色社会主义事业新局面，具有重大而深远的意义。 \n" +
//                "    开展集中教育历来是中国共产党加强自身建设的重要法宝。在党的发展历程中，每逢新形势、新任务，党内出现突出问题，我们党总是会集中一段时间和精力，通过开展党内教育活动凝聚共识、团结力量、提高战斗力，永葆先进性和旺盛生命力。十八大以来，党中央坚持问题导向，以空前力度开展了党的群众路线教育实践活动、“三严三实”专题教育和“两学一做”学习教育，使党的建设伟大工程步步为营、取得显著成效。但要巩固提高，防止和克服紧一阵松一阵、表面化形式化、学习教育与思想工作实际“两张皮”等不良倾向，持续深入解决存在的问题，还须在常态化制度化上下功夫。 \n" +
//                "    政贵有恒，治须有常。常态化的要义在于把阶段性转变为经常性和长期性，如滴水穿石般驰而不息。制度化的要义在于通过体制机制的方式将好的做法固定下来，便于大家共同遵守和执行。推进“两学一做”学习教育常态化制度化，要求各级党委（党组）以理论学习中心组学习、民主生活会等制度为抓手，组织党员领导干部定期开展集体学习；基层党组织以“三会一课”为基本制度，以党支部为基本单位，把“两学一做”作为党员教育的基本内容，长期坚持，形成常态，促使全面从严治党的状态持续巩固下去、深化下去。 \n" +
//                "    春风化雨，润物无声。推进“两学一做”学习教育常态化制度化，只有锲而不舍、长期坚持，久久为功、善作善成，在“化”上见成效，才能涵养成党内的蔚然风气，取得长期效果。各级党组织尤其是基层党支部只有结合工作实际，坚持问题导向，有什么问题解决什么问题，什么问题突出重点解决什么问题，才能发挥好战斗堡垒作用，把学习教育转化为实实在在的成效，真正让群众满意。每一个党员只有按照“四讲四有”标准，把“两学一做”转化为自然习惯，不断自省修身、打扫思想灰尘、进行“党性体检”，学思践悟、知行合一，做到政治合格、执行纪律合格、品德合格、发挥作用合格，才能把“共产党员跟我上”的鲜明旗帜立起来，保持与时代同步，踏实践行好全心全意为人民服务的宗旨，做人民群众心目中的党员。\n");
//        site.setTitle("zhoukai2");
//        site.setDeleted(false);
//        int success = siteService.insert(site);
//        System.out.println(success);
//    }
//
//    @Test
//    public void testP() throws Exception {
//        Process process = new Process();
//        process.setId("1");
//        process.setSiteId("3016097080873024");
//        process.setBeginTime(new Date());
//        process.setEndTime(new Date());
//        process.setStatus(1);
//        process.setMessage("no");
//        int success = processService.insert(process);
//        System.out.println(success);
//    }
//
//    @Test
//    public void testMapper() throws Exception {
//        Site site = new Site();
////        site.setId(3016097220505602L);
////        site.setTitle("人民网军事");
//        Response response = siteService.select(site, 1, 5);
//        System.out.println(response.getResult().size());
//    }
////    @Test
////    public void testSpiderFetchCount() throws Exception {
////        //构造添加的用户信息
////        String date = "2017-04-05";
////
////        //调用接口，传入添加的用户参数
////        mockMvc.perform(post("/pushFetchCountByDate")
////                .contentType(MediaType.APPLICATION_JSON_UTF8)
////                .content(mapper.writeValueAsString(userInfo)))
////                //判断返回值，是否达到预期，测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
////                //使用jsonPath解析返回值，判断具体的内容
////                .andExpect(jsonPath("$.errcode", is(0)))
////                .andExpect(jsonPath("$.p2pdata", notNullValue()))
////                .andExpect(jsonPath("$.p2pdata.id", not(0)))
////                .andExpect(jsonPath("$.p2pdata.name", is("testuser2")));
////    }
//}