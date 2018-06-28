package com.zhengjr.app.controller.userinfo;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ProductGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.ProductGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.zhengjr.app.bean.UserInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/userInfos")
@RestController
public class UserInfoController {

    @Autowired
    JdbcTemplate  mJdbcTemplate;

    @GetMapping("/getUsers")
    public List<Map<String, Object>> getDbType(){
        String sql = "select * from app_userinfo";
        List<Map<String, Object>> list = mJdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            Set<Map.Entry<String, Object>> entries = map.entrySet( );
            if(entries != null) {
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator( );
                while(iterator.hasNext( )) {
                    Map.Entry<String, Object> entry =(Map.Entry<String, Object>) iterator.next( );
                    Object key = entry.getKey( );
                    Object value = entry.getValue();
                    System.out.println(key+":"+value);
                }
            }
        }
        return list;
    }

    @Autowired
    private UserInfoBean mUserInfoBean;
    @Value(value = "${baichuanUrl}")
    private String mBaiChuanUrl;

    @Value(value = "${baichuanAppKey}")
    private String mBaiChuanAppKey;

    @Value(value = "${baichuanAppSecret}")
    private String mBaiChuanAppSecret;

    @RequestMapping(value = "/userInfo/{id}",method = RequestMethod.GET)
    public String userInfo(@PathVariable(value = "id",required = true)Integer id){
        testTaoDataLib();
        return mUserInfoBean.toString();
    }

    @PostMapping(value = "/postUserInfo")
    public String postUserInfo(){
        return "postUserInfo";
    }

    @PostMapping(value = "/postUserInfoParam")
    public String postUserInfo(@RequestParam(value = "name",required = true,defaultValue = "系统默认")String name){
        mUserInfoBean.setName(name);
        return "postUserInfo:" + mUserInfoBean.getName();
    }

    @GetMapping(value = "/getUserInfo")
    public String getUserInfo(){
        return "getUserInfo:" + mUserInfoBean.getName() + "--" + mUserInfoBean.getAge();
    }

    @GetMapping(value = "/{name}/getUserInfoParam/{age}")
    public String getUserInfo(@PathVariable("${name}")String name,@PathVariable("${age}")Integer age){
        mUserInfoBean.setName(name);
        mUserInfoBean.setAge(age);
        return "getUserInfo:" + mUserInfoBean.getName() + "--" + mUserInfoBean.getAge();
    }

    private String testTaoDataLib(){
        TaobaoClient client = new DefaultTaobaoClient(mBaiChuanUrl,mBaiChuanAppKey,mBaiChuanAppSecret);
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ("女装");
        req.setCat("16,18");
        req.setItemloc("杭州");
        req.setSort("tk_rate_des");
        req.setIsTmall(false);
        req.setIsOverseas(false);
        req.setStartPrice(10L);
        req.setEndPrice(10L);
        req.setStartTkRate(123L);
        req.setEndTkRate(123L);
        req.setPlatform(1L);
        req.setPageNo(123L);
        req.setPageSize(20L);
        try {
            TbkItemGetResponse rsp = client.execute(req);
            System.out.println(rsp.getBody());
            return rsp.getBody();

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return "";

    }

    private void getProductGetRequest() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest","24920169","fa5508edae9abaf299055f4ef89219f2");
        ProductGetRequest req = new ProductGetRequest();
        req.setFields("product_id,outer_id");
        req.setProductId(86126527L);
        req.setCid(50012286L);
        req.setProps("10005:10027;10006:29729");
        ProductGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
    }
}
