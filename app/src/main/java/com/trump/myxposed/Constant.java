package com.trump.myxposed;

public interface Constant {

    interface PackageIds {
        String wechat = "com.tencent.mm";
        String weico = "com.weico.international"; //微博国际版
    }

    String SpFileName = "MyXposed";

    interface SpKey {
        String weicoHomeAddButton = "weicoHomeAddButton";
    }

    /**
     * 各个加固厂商的入口特征
     */
    interface StubName {
        //360加固
        String c360 = "com.stub.StubApp";
        //腾讯御安全
        String cTenctent = "com.wrapper.proxyapplication.WrapperProxyApplication";
        //梆梆加固
        String cBangBang = "com.SecShell.SecShell.AW";
        //百度加固
        String cBaiDu = "com.sagittarius.v6.StubApplication";
        //爱加密加固
        String cAiJiaMi = "coms.h.e.l.l.S";  // 这里hook 需要采用log.e去打log 不然直接拦截 只有log.e ijm才会放行
        //...

    }
}
