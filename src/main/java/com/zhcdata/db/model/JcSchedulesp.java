package com.zhcdata.db.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "jc_schedulesp")
public class JcSchedulesp implements Serializable {
    @Column(name = "spID")
    private String spid;

    @Column(name = "ID")
    private String id;

    private String kind;

    private String wl3;

    private String wl1;

    private String wl0;

    @Column(name = "wlEnd")
    private String wlend;

    private String t0;

    private String t1;

    private String t2;

    private String t3;

    private String t4;

    private String t5;

    private String t6;

    private String t7;

    @Column(name = "tEnd")
    private String tend;

    private String sw10;

    private String sw20;

    private String sw21;

    private String sw30;

    private String sw31;

    private String sw32;

    private String sw40;

    private String sw41;

    private String sw42;

    private String sw50;

    private String sw51;

    private String sw52;

    private String sw5;

    private String sd00;

    private String sd11;

    private String sd22;

    private String sd33;

    private String sd4;

    private String sl01;

    private String sl02;

    private String sl12;

    private String sl03;

    private String sl13;

    private String sl23;

    private String sl04;

    private String sl14;

    private String sl24;

    private String sl05;

    private String sl15;

    private String sl25;

    private String sl5;

    @Column(name = "sEnd")
    private String send;

    private String ht33;

    private String ht31;

    private String ht30;

    private String ht13;

    private String ht11;

    private String ht10;

    private String ht03;

    private String ht01;

    private String ht00;

    @Column(name = "htEnd")
    private String htend;

    @Column(name = "UpdateTime")
    private String updatetime;

    @Column(name = "wlStop")
    private String wlstop;

    @Column(name = "tStop")
    private String tstop;

    @Column(name = "sStop")
    private String sstop;

    @Column(name = "htStop")
    private String htstop;

    private String sf3;

    private String sf1;

    private String sf0;

    @Column(name = "sfEnd")
    private String sfend;

    @Column(name = "sfStop")
    private String sfstop;

    @Column(name = "first_wl3")
    private String firstWl3;

    @Column(name = "first_wl1")
    private String firstWl1;

    @Column(name = "first_wl0")
    private String firstWl0;

    @Column(name = "first_sf3")
    private String firstSf3;

    @Column(name = "first_sf1")
    private String firstSf1;

    @Column(name = "first_sf0")
    private String firstSf0;

    private static final long serialVersionUID = 1L;

    /**
     * @return spID
     */
    public String getSpid() {
        return spid;
    }

    /**
     * @param spid
     */
    public void setSpid(String spid) {
        this.spid = spid;
    }

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return wl3
     */
    public String getWl3() {
        return wl3;
    }

    /**
     * @param wl3
     */
    public void setWl3(String wl3) {
        this.wl3 = wl3;
    }

    /**
     * @return wl1
     */
    public String getWl1() {
        return wl1;
    }

    /**
     * @param wl1
     */
    public void setWl1(String wl1) {
        this.wl1 = wl1;
    }

    /**
     * @return wl0
     */
    public String getWl0() {
        return wl0;
    }

    /**
     * @param wl0
     */
    public void setWl0(String wl0) {
        this.wl0 = wl0;
    }

    /**
     * @return wlEnd
     */
    public String getWlend() {
        return wlend;
    }

    /**
     * @param wlend
     */
    public void setWlend(String wlend) {
        this.wlend = wlend;
    }

    /**
     * @return t0
     */
    public String getT0() {
        return t0;
    }

    /**
     * @param t0
     */
    public void setT0(String t0) {
        this.t0 = t0;
    }

    /**
     * @return t1
     */
    public String getT1() {
        return t1;
    }

    /**
     * @param t1
     */
    public void setT1(String t1) {
        this.t1 = t1;
    }

    /**
     * @return t2
     */
    public String getT2() {
        return t2;
    }

    /**
     * @param t2
     */
    public void setT2(String t2) {
        this.t2 = t2;
    }

    /**
     * @return t3
     */
    public String getT3() {
        return t3;
    }

    /**
     * @param t3
     */
    public void setT3(String t3) {
        this.t3 = t3;
    }

    /**
     * @return t4
     */
    public String getT4() {
        return t4;
    }

    /**
     * @param t4
     */
    public void setT4(String t4) {
        this.t4 = t4;
    }

    /**
     * @return t5
     */
    public String getT5() {
        return t5;
    }

    /**
     * @param t5
     */
    public void setT5(String t5) {
        this.t5 = t5;
    }

    /**
     * @return t6
     */
    public String getT6() {
        return t6;
    }

    /**
     * @param t6
     */
    public void setT6(String t6) {
        this.t6 = t6;
    }

    /**
     * @return t7
     */
    public String getT7() {
        return t7;
    }

    /**
     * @param t7
     */
    public void setT7(String t7) {
        this.t7 = t7;
    }

    /**
     * @return tEnd
     */
    public String getTend() {
        return tend;
    }

    /**
     * @param tend
     */
    public void setTend(String tend) {
        this.tend = tend;
    }

    /**
     * @return sw10
     */
    public String getSw10() {
        return sw10;
    }

    /**
     * @param sw10
     */
    public void setSw10(String sw10) {
        this.sw10 = sw10;
    }

    /**
     * @return sw20
     */
    public String getSw20() {
        return sw20;
    }

    /**
     * @param sw20
     */
    public void setSw20(String sw20) {
        this.sw20 = sw20;
    }

    /**
     * @return sw21
     */
    public String getSw21() {
        return sw21;
    }

    /**
     * @param sw21
     */
    public void setSw21(String sw21) {
        this.sw21 = sw21;
    }

    /**
     * @return sw30
     */
    public String getSw30() {
        return sw30;
    }

    /**
     * @param sw30
     */
    public void setSw30(String sw30) {
        this.sw30 = sw30;
    }

    /**
     * @return sw31
     */
    public String getSw31() {
        return sw31;
    }

    /**
     * @param sw31
     */
    public void setSw31(String sw31) {
        this.sw31 = sw31;
    }

    /**
     * @return sw32
     */
    public String getSw32() {
        return sw32;
    }

    /**
     * @param sw32
     */
    public void setSw32(String sw32) {
        this.sw32 = sw32;
    }

    /**
     * @return sw40
     */
    public String getSw40() {
        return sw40;
    }

    /**
     * @param sw40
     */
    public void setSw40(String sw40) {
        this.sw40 = sw40;
    }

    /**
     * @return sw41
     */
    public String getSw41() {
        return sw41;
    }

    /**
     * @param sw41
     */
    public void setSw41(String sw41) {
        this.sw41 = sw41;
    }

    /**
     * @return sw42
     */
    public String getSw42() {
        return sw42;
    }

    /**
     * @param sw42
     */
    public void setSw42(String sw42) {
        this.sw42 = sw42;
    }

    /**
     * @return sw50
     */
    public String getSw50() {
        return sw50;
    }

    /**
     * @param sw50
     */
    public void setSw50(String sw50) {
        this.sw50 = sw50;
    }

    /**
     * @return sw51
     */
    public String getSw51() {
        return sw51;
    }

    /**
     * @param sw51
     */
    public void setSw51(String sw51) {
        this.sw51 = sw51;
    }

    /**
     * @return sw52
     */
    public String getSw52() {
        return sw52;
    }

    /**
     * @param sw52
     */
    public void setSw52(String sw52) {
        this.sw52 = sw52;
    }

    /**
     * @return sw5
     */
    public String getSw5() {
        return sw5;
    }

    /**
     * @param sw5
     */
    public void setSw5(String sw5) {
        this.sw5 = sw5;
    }

    /**
     * @return sd00
     */
    public String getSd00() {
        return sd00;
    }

    /**
     * @param sd00
     */
    public void setSd00(String sd00) {
        this.sd00 = sd00;
    }

    /**
     * @return sd11
     */
    public String getSd11() {
        return sd11;
    }

    /**
     * @param sd11
     */
    public void setSd11(String sd11) {
        this.sd11 = sd11;
    }

    /**
     * @return sd22
     */
    public String getSd22() {
        return sd22;
    }

    /**
     * @param sd22
     */
    public void setSd22(String sd22) {
        this.sd22 = sd22;
    }

    /**
     * @return sd33
     */
    public String getSd33() {
        return sd33;
    }

    /**
     * @param sd33
     */
    public void setSd33(String sd33) {
        this.sd33 = sd33;
    }

    /**
     * @return sd4
     */
    public String getSd4() {
        return sd4;
    }

    /**
     * @param sd4
     */
    public void setSd4(String sd4) {
        this.sd4 = sd4;
    }

    /**
     * @return sl01
     */
    public String getSl01() {
        return sl01;
    }

    /**
     * @param sl01
     */
    public void setSl01(String sl01) {
        this.sl01 = sl01;
    }

    /**
     * @return sl02
     */
    public String getSl02() {
        return sl02;
    }

    /**
     * @param sl02
     */
    public void setSl02(String sl02) {
        this.sl02 = sl02;
    }

    /**
     * @return sl12
     */
    public String getSl12() {
        return sl12;
    }

    /**
     * @param sl12
     */
    public void setSl12(String sl12) {
        this.sl12 = sl12;
    }

    /**
     * @return sl03
     */
    public String getSl03() {
        return sl03;
    }

    /**
     * @param sl03
     */
    public void setSl03(String sl03) {
        this.sl03 = sl03;
    }

    /**
     * @return sl13
     */
    public String getSl13() {
        return sl13;
    }

    /**
     * @param sl13
     */
    public void setSl13(String sl13) {
        this.sl13 = sl13;
    }

    /**
     * @return sl23
     */
    public String getSl23() {
        return sl23;
    }

    /**
     * @param sl23
     */
    public void setSl23(String sl23) {
        this.sl23 = sl23;
    }

    /**
     * @return sl04
     */
    public String getSl04() {
        return sl04;
    }

    /**
     * @param sl04
     */
    public void setSl04(String sl04) {
        this.sl04 = sl04;
    }

    /**
     * @return sl14
     */
    public String getSl14() {
        return sl14;
    }

    /**
     * @param sl14
     */
    public void setSl14(String sl14) {
        this.sl14 = sl14;
    }

    /**
     * @return sl24
     */
    public String getSl24() {
        return sl24;
    }

    /**
     * @param sl24
     */
    public void setSl24(String sl24) {
        this.sl24 = sl24;
    }

    /**
     * @return sl05
     */
    public String getSl05() {
        return sl05;
    }

    /**
     * @param sl05
     */
    public void setSl05(String sl05) {
        this.sl05 = sl05;
    }

    /**
     * @return sl15
     */
    public String getSl15() {
        return sl15;
    }

    /**
     * @param sl15
     */
    public void setSl15(String sl15) {
        this.sl15 = sl15;
    }

    /**
     * @return sl25
     */
    public String getSl25() {
        return sl25;
    }

    /**
     * @param sl25
     */
    public void setSl25(String sl25) {
        this.sl25 = sl25;
    }

    /**
     * @return sl5
     */
    public String getSl5() {
        return sl5;
    }

    /**
     * @param sl5
     */
    public void setSl5(String sl5) {
        this.sl5 = sl5;
    }

    /**
     * @return sEnd
     */
    public String getSend() {
        return send;
    }

    /**
     * @param send
     */
    public void setSend(String send) {
        this.send = send;
    }

    /**
     * @return ht33
     */
    public String getHt33() {
        return ht33;
    }

    /**
     * @param ht33
     */
    public void setHt33(String ht33) {
        this.ht33 = ht33;
    }

    /**
     * @return ht31
     */
    public String getHt31() {
        return ht31;
    }

    /**
     * @param ht31
     */
    public void setHt31(String ht31) {
        this.ht31 = ht31;
    }

    /**
     * @return ht30
     */
    public String getHt30() {
        return ht30;
    }

    /**
     * @param ht30
     */
    public void setHt30(String ht30) {
        this.ht30 = ht30;
    }

    /**
     * @return ht13
     */
    public String getHt13() {
        return ht13;
    }

    /**
     * @param ht13
     */
    public void setHt13(String ht13) {
        this.ht13 = ht13;
    }

    /**
     * @return ht11
     */
    public String getHt11() {
        return ht11;
    }

    /**
     * @param ht11
     */
    public void setHt11(String ht11) {
        this.ht11 = ht11;
    }

    /**
     * @return ht10
     */
    public String getHt10() {
        return ht10;
    }

    /**
     * @param ht10
     */
    public void setHt10(String ht10) {
        this.ht10 = ht10;
    }

    /**
     * @return ht03
     */
    public String getHt03() {
        return ht03;
    }

    /**
     * @param ht03
     */
    public void setHt03(String ht03) {
        this.ht03 = ht03;
    }

    /**
     * @return ht01
     */
    public String getHt01() {
        return ht01;
    }

    /**
     * @param ht01
     */
    public void setHt01(String ht01) {
        this.ht01 = ht01;
    }

    /**
     * @return ht00
     */
    public String getHt00() {
        return ht00;
    }

    /**
     * @param ht00
     */
    public void setHt00(String ht00) {
        this.ht00 = ht00;
    }

    /**
     * @return htEnd
     */
    public String getHtend() {
        return htend;
    }

    /**
     * @param htend
     */
    public void setHtend(String htend) {
        this.htend = htend;
    }

    /**
     * @return UpdateTime
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return wlStop
     */
    public String getWlstop() {
        return wlstop;
    }

    /**
     * @param wlstop
     */
    public void setWlstop(String wlstop) {
        this.wlstop = wlstop;
    }

    /**
     * @return tStop
     */
    public String getTstop() {
        return tstop;
    }

    /**
     * @param tstop
     */
    public void setTstop(String tstop) {
        this.tstop = tstop;
    }

    /**
     * @return sStop
     */
    public String getSstop() {
        return sstop;
    }

    /**
     * @param sstop
     */
    public void setSstop(String sstop) {
        this.sstop = sstop;
    }

    /**
     * @return htStop
     */
    public String getHtstop() {
        return htstop;
    }

    /**
     * @param htstop
     */
    public void setHtstop(String htstop) {
        this.htstop = htstop;
    }

    /**
     * @return sf3
     */
    public String getSf3() {
        return sf3;
    }

    /**
     * @param sf3
     */
    public void setSf3(String sf3) {
        this.sf3 = sf3;
    }

    /**
     * @return sf1
     */
    public String getSf1() {
        return sf1;
    }

    /**
     * @param sf1
     */
    public void setSf1(String sf1) {
        this.sf1 = sf1;
    }

    /**
     * @return sf0
     */
    public String getSf0() {
        return sf0;
    }

    /**
     * @param sf0
     */
    public void setSf0(String sf0) {
        this.sf0 = sf0;
    }

    /**
     * @return sfEnd
     */
    public String getSfend() {
        return sfend;
    }

    /**
     * @param sfend
     */
    public void setSfend(String sfend) {
        this.sfend = sfend;
    }

    /**
     * @return sfStop
     */
    public String getSfstop() {
        return sfstop;
    }

    /**
     * @param sfstop
     */
    public void setSfstop(String sfstop) {
        this.sfstop = sfstop;
    }

    /**
     * @return first_wl3
     */
    public String getFirstWl3() {
        return firstWl3;
    }

    /**
     * @param firstWl3
     */
    public void setFirstWl3(String firstWl3) {
        this.firstWl3 = firstWl3;
    }

    /**
     * @return first_wl1
     */
    public String getFirstWl1() {
        return firstWl1;
    }

    /**
     * @param firstWl1
     */
    public void setFirstWl1(String firstWl1) {
        this.firstWl1 = firstWl1;
    }

    /**
     * @return first_wl0
     */
    public String getFirstWl0() {
        return firstWl0;
    }

    /**
     * @param firstWl0
     */
    public void setFirstWl0(String firstWl0) {
        this.firstWl0 = firstWl0;
    }

    /**
     * @return first_sf3
     */
    public String getFirstSf3() {
        return firstSf3;
    }

    /**
     * @param firstSf3
     */
    public void setFirstSf3(String firstSf3) {
        this.firstSf3 = firstSf3;
    }

    /**
     * @return first_sf1
     */
    public String getFirstSf1() {
        return firstSf1;
    }

    /**
     * @param firstSf1
     */
    public void setFirstSf1(String firstSf1) {
        this.firstSf1 = firstSf1;
    }

    /**
     * @return first_sf0
     */
    public String getFirstSf0() {
        return firstSf0;
    }

    /**
     * @param firstSf0
     */
    public void setFirstSf0(String firstSf0) {
        this.firstSf0 = firstSf0;
    }
}