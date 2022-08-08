/**
 * PrjDtlInfoVo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.digitalLab.atis.prjdtlinfo;

public class PrjDtlInfoVo  implements java.io.Serializable {
    private java.lang.String countControl;

    private int findPage;

    private java.lang.String findPrjDtlNm;

    private java.lang.String findPrjDtlNo;

    private java.lang.String findPrjDtlRsprDicCode;

    private java.lang.String findPrjDtlRsprNm;

    private java.lang.String findPrjDtlYear;

    private java.lang.String findPrtcpMp;

    private java.lang.String findPrtcpMpDicCode;

    private int findRowPerPage;

    private java.lang.String korKywd;

    private java.lang.String mainRsprDeptNm;

    private java.lang.String mainRsprNm;

    private java.lang.String mainRsprTopDeptNm;

    private int pageNo;

    private java.lang.Object[] prjDtlInfoList;

    private java.lang.String prjDtlNm;

    private java.lang.String prjDtlNo;

    private java.lang.String prjDtlRsprDicCode;

    private java.lang.String prjNm;

    private java.lang.String prjNo;

    private java.lang.String prjTypeNm;

    private java.lang.String prtcpMpDicCodeList;

    private java.lang.String prtcpMpList;

    private java.lang.String reaultFlag;

    private java.lang.String resultMsg;

    private java.lang.String rndPhaseNm;

    private java.lang.String rndcoTotAmt;

    private java.lang.String subRsprDeptNm;

    private java.lang.String subRsprNm;

    private java.lang.String subRsprTopDeptNm;

    private java.lang.String sysId;

    private java.lang.String sysPwd;

    private java.lang.String totRschEndDt;

    private java.lang.String totRschEndYear;

    private java.lang.String totRschStartDt;

    private java.lang.String totRschStartYear;

    private int totalRow;

    private java.lang.String updateDt;

    private java.lang.String yearCnt;

    public PrjDtlInfoVo() {
    }

    public PrjDtlInfoVo(
           java.lang.String countControl,
           int findPage,
           java.lang.String findPrjDtlNm,
           java.lang.String findPrjDtlNo,
           java.lang.String findPrjDtlRsprDicCode,
           java.lang.String findPrjDtlRsprNm,
           java.lang.String findPrjDtlYear,
           java.lang.String findPrtcpMp,
           java.lang.String findPrtcpMpDicCode,
           int findRowPerPage,
           java.lang.String korKywd,
           java.lang.String mainRsprDeptNm,
           java.lang.String mainRsprNm,
           java.lang.String mainRsprTopDeptNm,
           int pageNo,
           java.lang.Object[] prjDtlInfoList,
           java.lang.String prjDtlNm,
           java.lang.String prjDtlNo,
           java.lang.String prjDtlRsprDicCode,
           java.lang.String prjNm,
           java.lang.String prjNo,
           java.lang.String prjTypeNm,
           java.lang.String prtcpMpDicCodeList,
           java.lang.String prtcpMpList,
           java.lang.String reaultFlag,
           java.lang.String resultMsg,
           java.lang.String rndPhaseNm,
           java.lang.String rndcoTotAmt,
           java.lang.String subRsprDeptNm,
           java.lang.String subRsprNm,
           java.lang.String subRsprTopDeptNm,
           java.lang.String sysId,
           java.lang.String sysPwd,
           java.lang.String totRschEndDt,
           java.lang.String totRschEndYear,
           java.lang.String totRschStartDt,
           java.lang.String totRschStartYear,
           int totalRow,
           java.lang.String updateDt,
           java.lang.String yearCnt) {
           this.countControl = countControl;
           this.findPage = findPage;
           this.findPrjDtlNm = findPrjDtlNm;
           this.findPrjDtlNo = findPrjDtlNo;
           this.findPrjDtlRsprDicCode = findPrjDtlRsprDicCode;
           this.findPrjDtlRsprNm = findPrjDtlRsprNm;
           this.findPrjDtlYear = findPrjDtlYear;
           this.findPrtcpMp = findPrtcpMp;
           this.findPrtcpMpDicCode = findPrtcpMpDicCode;
           this.findRowPerPage = findRowPerPage;
           this.korKywd = korKywd;
           this.mainRsprDeptNm = mainRsprDeptNm;
           this.mainRsprNm = mainRsprNm;
           this.mainRsprTopDeptNm = mainRsprTopDeptNm;
           this.pageNo = pageNo;
           this.prjDtlInfoList = prjDtlInfoList;
           this.prjDtlNm = prjDtlNm;
           this.prjDtlNo = prjDtlNo;
           this.prjDtlRsprDicCode = prjDtlRsprDicCode;
           this.prjNm = prjNm;
           this.prjNo = prjNo;
           this.prjTypeNm = prjTypeNm;
           this.prtcpMpDicCodeList = prtcpMpDicCodeList;
           this.prtcpMpList = prtcpMpList;
           this.reaultFlag = reaultFlag;
           this.resultMsg = resultMsg;
           this.rndPhaseNm = rndPhaseNm;
           this.rndcoTotAmt = rndcoTotAmt;
           this.subRsprDeptNm = subRsprDeptNm;
           this.subRsprNm = subRsprNm;
           this.subRsprTopDeptNm = subRsprTopDeptNm;
           this.sysId = sysId;
           this.sysPwd = sysPwd;
           this.totRschEndDt = totRschEndDt;
           this.totRschEndYear = totRschEndYear;
           this.totRschStartDt = totRschStartDt;
           this.totRschStartYear = totRschStartYear;
           this.totalRow = totalRow;
           this.updateDt = updateDt;
           this.yearCnt = yearCnt;
    }


    /**
     * Gets the countControl value for this PrjDtlInfoVo.
     * 
     * @return countControl
     */
    public java.lang.String getCountControl() {
        return countControl;
    }


    /**
     * Sets the countControl value for this PrjDtlInfoVo.
     * 
     * @param countControl
     */
    public void setCountControl(java.lang.String countControl) {
        this.countControl = countControl;
    }


    /**
     * Gets the findPage value for this PrjDtlInfoVo.
     * 
     * @return findPage
     */
    public int getFindPage() {
        return findPage;
    }


    /**
     * Sets the findPage value for this PrjDtlInfoVo.
     * 
     * @param findPage
     */
    public void setFindPage(int findPage) {
        this.findPage = findPage;
    }


    /**
     * Gets the findPrjDtlNm value for this PrjDtlInfoVo.
     * 
     * @return findPrjDtlNm
     */
    public java.lang.String getFindPrjDtlNm() {
        return findPrjDtlNm;
    }


    /**
     * Sets the findPrjDtlNm value for this PrjDtlInfoVo.
     * 
     * @param findPrjDtlNm
     */
    public void setFindPrjDtlNm(java.lang.String findPrjDtlNm) {
        this.findPrjDtlNm = findPrjDtlNm;
    }


    /**
     * Gets the findPrjDtlNo value for this PrjDtlInfoVo.
     * 
     * @return findPrjDtlNo
     */
    public java.lang.String getFindPrjDtlNo() {
        return findPrjDtlNo;
    }


    /**
     * Sets the findPrjDtlNo value for this PrjDtlInfoVo.
     * 
     * @param findPrjDtlNo
     */
    public void setFindPrjDtlNo(java.lang.String findPrjDtlNo) {
        this.findPrjDtlNo = findPrjDtlNo;
    }


    /**
     * Gets the findPrjDtlRsprDicCode value for this PrjDtlInfoVo.
     * 
     * @return findPrjDtlRsprDicCode
     */
    public java.lang.String getFindPrjDtlRsprDicCode() {
        return findPrjDtlRsprDicCode;
    }


    /**
     * Sets the findPrjDtlRsprDicCode value for this PrjDtlInfoVo.
     * 
     * @param findPrjDtlRsprDicCode
     */
    public void setFindPrjDtlRsprDicCode(java.lang.String findPrjDtlRsprDicCode) {
        this.findPrjDtlRsprDicCode = findPrjDtlRsprDicCode;
    }


    /**
     * Gets the findPrjDtlRsprNm value for this PrjDtlInfoVo.
     * 
     * @return findPrjDtlRsprNm
     */
    public java.lang.String getFindPrjDtlRsprNm() {
        return findPrjDtlRsprNm;
    }


    /**
     * Sets the findPrjDtlRsprNm value for this PrjDtlInfoVo.
     * 
     * @param findPrjDtlRsprNm
     */
    public void setFindPrjDtlRsprNm(java.lang.String findPrjDtlRsprNm) {
        this.findPrjDtlRsprNm = findPrjDtlRsprNm;
    }


    /**
     * Gets the findPrjDtlYear value for this PrjDtlInfoVo.
     * 
     * @return findPrjDtlYear
     */
    public java.lang.String getFindPrjDtlYear() {
        return findPrjDtlYear;
    }


    /**
     * Sets the findPrjDtlYear value for this PrjDtlInfoVo.
     * 
     * @param findPrjDtlYear
     */
    public void setFindPrjDtlYear(java.lang.String findPrjDtlYear) {
        this.findPrjDtlYear = findPrjDtlYear;
    }


    /**
     * Gets the findPrtcpMp value for this PrjDtlInfoVo.
     * 
     * @return findPrtcpMp
     */
    public java.lang.String getFindPrtcpMp() {
        return findPrtcpMp;
    }


    /**
     * Sets the findPrtcpMp value for this PrjDtlInfoVo.
     * 
     * @param findPrtcpMp
     */
    public void setFindPrtcpMp(java.lang.String findPrtcpMp) {
        this.findPrtcpMp = findPrtcpMp;
    }


    /**
     * Gets the findPrtcpMpDicCode value for this PrjDtlInfoVo.
     * 
     * @return findPrtcpMpDicCode
     */
    public java.lang.String getFindPrtcpMpDicCode() {
        return findPrtcpMpDicCode;
    }


    /**
     * Sets the findPrtcpMpDicCode value for this PrjDtlInfoVo.
     * 
     * @param findPrtcpMpDicCode
     */
    public void setFindPrtcpMpDicCode(java.lang.String findPrtcpMpDicCode) {
        this.findPrtcpMpDicCode = findPrtcpMpDicCode;
    }


    /**
     * Gets the findRowPerPage value for this PrjDtlInfoVo.
     * 
     * @return findRowPerPage
     */
    public int getFindRowPerPage() {
        return findRowPerPage;
    }


    /**
     * Sets the findRowPerPage value for this PrjDtlInfoVo.
     * 
     * @param findRowPerPage
     */
    public void setFindRowPerPage(int findRowPerPage) {
        this.findRowPerPage = findRowPerPage;
    }


    /**
     * Gets the korKywd value for this PrjDtlInfoVo.
     * 
     * @return korKywd
     */
    public java.lang.String getKorKywd() {
        return korKywd;
    }


    /**
     * Sets the korKywd value for this PrjDtlInfoVo.
     * 
     * @param korKywd
     */
    public void setKorKywd(java.lang.String korKywd) {
        this.korKywd = korKywd;
    }


    /**
     * Gets the mainRsprDeptNm value for this PrjDtlInfoVo.
     * 
     * @return mainRsprDeptNm
     */
    public java.lang.String getMainRsprDeptNm() {
        return mainRsprDeptNm;
    }


    /**
     * Sets the mainRsprDeptNm value for this PrjDtlInfoVo.
     * 
     * @param mainRsprDeptNm
     */
    public void setMainRsprDeptNm(java.lang.String mainRsprDeptNm) {
        this.mainRsprDeptNm = mainRsprDeptNm;
    }


    /**
     * Gets the mainRsprNm value for this PrjDtlInfoVo.
     * 
     * @return mainRsprNm
     */
    public java.lang.String getMainRsprNm() {
        return mainRsprNm;
    }


    /**
     * Sets the mainRsprNm value for this PrjDtlInfoVo.
     * 
     * @param mainRsprNm
     */
    public void setMainRsprNm(java.lang.String mainRsprNm) {
        this.mainRsprNm = mainRsprNm;
    }


    /**
     * Gets the mainRsprTopDeptNm value for this PrjDtlInfoVo.
     * 
     * @return mainRsprTopDeptNm
     */
    public java.lang.String getMainRsprTopDeptNm() {
        return mainRsprTopDeptNm;
    }


    /**
     * Sets the mainRsprTopDeptNm value for this PrjDtlInfoVo.
     * 
     * @param mainRsprTopDeptNm
     */
    public void setMainRsprTopDeptNm(java.lang.String mainRsprTopDeptNm) {
        this.mainRsprTopDeptNm = mainRsprTopDeptNm;
    }


    /**
     * Gets the pageNo value for this PrjDtlInfoVo.
     * 
     * @return pageNo
     */
    public int getPageNo() {
        return pageNo;
    }


    /**
     * Sets the pageNo value for this PrjDtlInfoVo.
     * 
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }


    /**
     * Gets the prjDtlInfoList value for this PrjDtlInfoVo.
     * 
     * @return prjDtlInfoList
     */
    public java.lang.Object[] getPrjDtlInfoList() {
        return this.prjDtlInfoList;
    }


    /**
     * Sets the prjDtlInfoList value for this PrjDtlInfoVo.
     * 
     * @param prjDtlInfoList
     */
    public void setPrjDtlInfoList(java.lang.Object[] prjDtlInfoList) {
        this.prjDtlInfoList = prjDtlInfoList;
    }


    /**
     * Gets the prjDtlNm value for this PrjDtlInfoVo.
     * 
     * @return prjDtlNm
     */
    public java.lang.String getPrjDtlNm() {
        return prjDtlNm;
    }


    /**
     * Sets the prjDtlNm value for this PrjDtlInfoVo.
     * 
     * @param prjDtlNm
     */
    public void setPrjDtlNm(java.lang.String prjDtlNm) {
        this.prjDtlNm = prjDtlNm;
    }


    /**
     * Gets the prjDtlNo value for this PrjDtlInfoVo.
     * 
     * @return prjDtlNo
     */
    public java.lang.String getPrjDtlNo() {
        return prjDtlNo;
    }


    /**
     * Sets the prjDtlNo value for this PrjDtlInfoVo.
     * 
     * @param prjDtlNo
     */
    public void setPrjDtlNo(java.lang.String prjDtlNo) {
        this.prjDtlNo = prjDtlNo;
    }


    /**
     * Gets the prjDtlRsprDicCode value for this PrjDtlInfoVo.
     * 
     * @return prjDtlRsprDicCode
     */
    public java.lang.String getPrjDtlRsprDicCode() {
        return prjDtlRsprDicCode;
    }


    /**
     * Sets the prjDtlRsprDicCode value for this PrjDtlInfoVo.
     * 
     * @param prjDtlRsprDicCode
     */
    public void setPrjDtlRsprDicCode(java.lang.String prjDtlRsprDicCode) {
        this.prjDtlRsprDicCode = prjDtlRsprDicCode;
    }


    /**
     * Gets the prjNm value for this PrjDtlInfoVo.
     * 
     * @return prjNm
     */
    public java.lang.String getPrjNm() {
        return prjNm;
    }


    /**
     * Sets the prjNm value for this PrjDtlInfoVo.
     * 
     * @param prjNm
     */
    public void setPrjNm(java.lang.String prjNm) {
        this.prjNm = prjNm;
    }


    /**
     * Gets the prjNo value for this PrjDtlInfoVo.
     * 
     * @return prjNo
     */
    public java.lang.String getPrjNo() {
        return prjNo;
    }


    /**
     * Sets the prjNo value for this PrjDtlInfoVo.
     * 
     * @param prjNo
     */
    public void setPrjNo(java.lang.String prjNo) {
        this.prjNo = prjNo;
    }


    /**
     * Gets the prjTypeNm value for this PrjDtlInfoVo.
     * 
     * @return prjTypeNm
     */
    public java.lang.String getPrjTypeNm() {
        return prjTypeNm;
    }


    /**
     * Sets the prjTypeNm value for this PrjDtlInfoVo.
     * 
     * @param prjTypeNm
     */
    public void setPrjTypeNm(java.lang.String prjTypeNm) {
        this.prjTypeNm = prjTypeNm;
    }


    /**
     * Gets the prtcpMpDicCodeList value for this PrjDtlInfoVo.
     * 
     * @return prtcpMpDicCodeList
     */
    public java.lang.String getPrtcpMpDicCodeList() {
        return prtcpMpDicCodeList;
    }


    /**
     * Sets the prtcpMpDicCodeList value for this PrjDtlInfoVo.
     * 
     * @param prtcpMpDicCodeList
     */
    public void setPrtcpMpDicCodeList(java.lang.String prtcpMpDicCodeList) {
        this.prtcpMpDicCodeList = prtcpMpDicCodeList;
    }


    /**
     * Gets the prtcpMpList value for this PrjDtlInfoVo.
     * 
     * @return prtcpMpList
     */
    public java.lang.String getPrtcpMpList() {
        return prtcpMpList;
    }


    /**
     * Sets the prtcpMpList value for this PrjDtlInfoVo.
     * 
     * @param prtcpMpList
     */
    public void setPrtcpMpList(java.lang.String prtcpMpList) {
        this.prtcpMpList = prtcpMpList;
    }


    /**
     * Gets the reaultFlag value for this PrjDtlInfoVo.
     * 
     * @return reaultFlag
     */
    public java.lang.String getReaultFlag() {
        return reaultFlag;
    }


    /**
     * Sets the reaultFlag value for this PrjDtlInfoVo.
     * 
     * @param reaultFlag
     */
    public void setReaultFlag(java.lang.String reaultFlag) {
        this.reaultFlag = reaultFlag;
    }


    /**
     * Gets the resultMsg value for this PrjDtlInfoVo.
     * 
     * @return resultMsg
     */
    public java.lang.String getResultMsg() {
        return resultMsg;
    }


    /**
     * Sets the resultMsg value for this PrjDtlInfoVo.
     * 
     * @param resultMsg
     */
    public void setResultMsg(java.lang.String resultMsg) {
        this.resultMsg = resultMsg;
    }


    /**
     * Gets the rndPhaseNm value for this PrjDtlInfoVo.
     * 
     * @return rndPhaseNm
     */
    public java.lang.String getRndPhaseNm() {
        return rndPhaseNm;
    }


    /**
     * Sets the rndPhaseNm value for this PrjDtlInfoVo.
     * 
     * @param rndPhaseNm
     */
    public void setRndPhaseNm(java.lang.String rndPhaseNm) {
        this.rndPhaseNm = rndPhaseNm;
    }


    /**
     * Gets the rndcoTotAmt value for this PrjDtlInfoVo.
     * 
     * @return rndcoTotAmt
     */
    public java.lang.String getRndcoTotAmt() {
        return rndcoTotAmt;
    }


    /**
     * Sets the rndcoTotAmt value for this PrjDtlInfoVo.
     * 
     * @param rndcoTotAmt
     */
    public void setRndcoTotAmt(java.lang.String rndcoTotAmt) {
        this.rndcoTotAmt = rndcoTotAmt;
    }


    /**
     * Gets the subRsprDeptNm value for this PrjDtlInfoVo.
     * 
     * @return subRsprDeptNm
     */
    public java.lang.String getSubRsprDeptNm() {
        return subRsprDeptNm;
    }


    /**
     * Sets the subRsprDeptNm value for this PrjDtlInfoVo.
     * 
     * @param subRsprDeptNm
     */
    public void setSubRsprDeptNm(java.lang.String subRsprDeptNm) {
        this.subRsprDeptNm = subRsprDeptNm;
    }


    /**
     * Gets the subRsprNm value for this PrjDtlInfoVo.
     * 
     * @return subRsprNm
     */
    public java.lang.String getSubRsprNm() {
        return subRsprNm;
    }


    /**
     * Sets the subRsprNm value for this PrjDtlInfoVo.
     * 
     * @param subRsprNm
     */
    public void setSubRsprNm(java.lang.String subRsprNm) {
        this.subRsprNm = subRsprNm;
    }


    /**
     * Gets the subRsprTopDeptNm value for this PrjDtlInfoVo.
     * 
     * @return subRsprTopDeptNm
     */
    public java.lang.String getSubRsprTopDeptNm() {
        return subRsprTopDeptNm;
    }


    /**
     * Sets the subRsprTopDeptNm value for this PrjDtlInfoVo.
     * 
     * @param subRsprTopDeptNm
     */
    public void setSubRsprTopDeptNm(java.lang.String subRsprTopDeptNm) {
        this.subRsprTopDeptNm = subRsprTopDeptNm;
    }


    /**
     * Gets the sysId value for this PrjDtlInfoVo.
     * 
     * @return sysId
     */
    public java.lang.String getSysId() {
        return sysId;
    }


    /**
     * Sets the sysId value for this PrjDtlInfoVo.
     * 
     * @param sysId
     */
    public void setSysId(java.lang.String sysId) {
        this.sysId = sysId;
    }


    /**
     * Gets the sysPwd value for this PrjDtlInfoVo.
     * 
     * @return sysPwd
     */
    public java.lang.String getSysPwd() {
        return sysPwd;
    }


    /**
     * Sets the sysPwd value for this PrjDtlInfoVo.
     * 
     * @param sysPwd
     */
    public void setSysPwd(java.lang.String sysPwd) {
        this.sysPwd = sysPwd;
    }


    /**
     * Gets the totRschEndDt value for this PrjDtlInfoVo.
     * 
     * @return totRschEndDt
     */
    public java.lang.String getTotRschEndDt() {
        return totRschEndDt;
    }


    /**
     * Sets the totRschEndDt value for this PrjDtlInfoVo.
     * 
     * @param totRschEndDt
     */
    public void setTotRschEndDt(java.lang.String totRschEndDt) {
        this.totRschEndDt = totRschEndDt;
    }


    /**
     * Gets the totRschEndYear value for this PrjDtlInfoVo.
     * 
     * @return totRschEndYear
     */
    public java.lang.String getTotRschEndYear() {
        return totRschEndYear;
    }


    /**
     * Sets the totRschEndYear value for this PrjDtlInfoVo.
     * 
     * @param totRschEndYear
     */
    public void setTotRschEndYear(java.lang.String totRschEndYear) {
        this.totRschEndYear = totRschEndYear;
    }


    /**
     * Gets the totRschStartDt value for this PrjDtlInfoVo.
     * 
     * @return totRschStartDt
     */
    public java.lang.String getTotRschStartDt() {
        return totRschStartDt;
    }


    /**
     * Sets the totRschStartDt value for this PrjDtlInfoVo.
     * 
     * @param totRschStartDt
     */
    public void setTotRschStartDt(java.lang.String totRschStartDt) {
        this.totRschStartDt = totRschStartDt;
    }


    /**
     * Gets the totRschStartYear value for this PrjDtlInfoVo.
     * 
     * @return totRschStartYear
     */
    public java.lang.String getTotRschStartYear() {
        return totRschStartYear;
    }


    /**
     * Sets the totRschStartYear value for this PrjDtlInfoVo.
     * 
     * @param totRschStartYear
     */
    public void setTotRschStartYear(java.lang.String totRschStartYear) {
        this.totRschStartYear = totRschStartYear;
    }


    /**
     * Gets the totalRow value for this PrjDtlInfoVo.
     * 
     * @return totalRow
     */
    public int getTotalRow() {
        return totalRow;
    }


    /**
     * Sets the totalRow value for this PrjDtlInfoVo.
     * 
     * @param totalRow
     */
    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }


    /**
     * Gets the updateDt value for this PrjDtlInfoVo.
     * 
     * @return updateDt
     */
    public java.lang.String getUpdateDt() {
        return updateDt;
    }


    /**
     * Sets the updateDt value for this PrjDtlInfoVo.
     * 
     * @param updateDt
     */
    public void setUpdateDt(java.lang.String updateDt) {
        this.updateDt = updateDt;
    }


    /**
     * Gets the yearCnt value for this PrjDtlInfoVo.
     * 
     * @return yearCnt
     */
    public java.lang.String getYearCnt() {
        return yearCnt;
    }


    /**
     * Sets the yearCnt value for this PrjDtlInfoVo.
     * 
     * @param yearCnt
     */
    public void setYearCnt(java.lang.String yearCnt) {
        this.yearCnt = yearCnt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PrjDtlInfoVo)) return false;
        PrjDtlInfoVo other = (PrjDtlInfoVo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.countControl==null && other.getCountControl()==null) || 
             (this.countControl!=null &&
              this.countControl.equals(other.getCountControl()))) &&
            this.findPage == other.getFindPage() &&
            ((this.findPrjDtlNm==null && other.getFindPrjDtlNm()==null) || 
             (this.findPrjDtlNm!=null &&
              this.findPrjDtlNm.equals(other.getFindPrjDtlNm()))) &&
            ((this.findPrjDtlNo==null && other.getFindPrjDtlNo()==null) || 
             (this.findPrjDtlNo!=null &&
              this.findPrjDtlNo.equals(other.getFindPrjDtlNo()))) &&
            ((this.findPrjDtlRsprDicCode==null && other.getFindPrjDtlRsprDicCode()==null) || 
             (this.findPrjDtlRsprDicCode!=null &&
              this.findPrjDtlRsprDicCode.equals(other.getFindPrjDtlRsprDicCode()))) &&
            ((this.findPrjDtlRsprNm==null && other.getFindPrjDtlRsprNm()==null) || 
             (this.findPrjDtlRsprNm!=null &&
              this.findPrjDtlRsprNm.equals(other.getFindPrjDtlRsprNm()))) &&
            ((this.findPrjDtlYear==null && other.getFindPrjDtlYear()==null) || 
             (this.findPrjDtlYear!=null &&
              this.findPrjDtlYear.equals(other.getFindPrjDtlYear()))) &&
            ((this.findPrtcpMp==null && other.getFindPrtcpMp()==null) || 
             (this.findPrtcpMp!=null &&
              this.findPrtcpMp.equals(other.getFindPrtcpMp()))) &&
            ((this.findPrtcpMpDicCode==null && other.getFindPrtcpMpDicCode()==null) || 
             (this.findPrtcpMpDicCode!=null &&
              this.findPrtcpMpDicCode.equals(other.getFindPrtcpMpDicCode()))) &&
            this.findRowPerPage == other.getFindRowPerPage() &&
            ((this.korKywd==null && other.getKorKywd()==null) || 
             (this.korKywd!=null &&
              this.korKywd.equals(other.getKorKywd()))) &&
            ((this.mainRsprDeptNm==null && other.getMainRsprDeptNm()==null) || 
             (this.mainRsprDeptNm!=null &&
              this.mainRsprDeptNm.equals(other.getMainRsprDeptNm()))) &&
            ((this.mainRsprNm==null && other.getMainRsprNm()==null) || 
             (this.mainRsprNm!=null &&
              this.mainRsprNm.equals(other.getMainRsprNm()))) &&
            ((this.mainRsprTopDeptNm==null && other.getMainRsprTopDeptNm()==null) || 
             (this.mainRsprTopDeptNm!=null &&
              this.mainRsprTopDeptNm.equals(other.getMainRsprTopDeptNm()))) &&
            this.pageNo == other.getPageNo() &&
            ((this.prjDtlInfoList==null && other.getPrjDtlInfoList()==null) || 
             (this.prjDtlInfoList!=null &&
              java.util.Arrays.equals(this.prjDtlInfoList, other.getPrjDtlInfoList()))) &&
            ((this.prjDtlNm==null && other.getPrjDtlNm()==null) || 
             (this.prjDtlNm!=null &&
              this.prjDtlNm.equals(other.getPrjDtlNm()))) &&
            ((this.prjDtlNo==null && other.getPrjDtlNo()==null) || 
             (this.prjDtlNo!=null &&
              this.prjDtlNo.equals(other.getPrjDtlNo()))) &&
            ((this.prjDtlRsprDicCode==null && other.getPrjDtlRsprDicCode()==null) || 
             (this.prjDtlRsprDicCode!=null &&
              this.prjDtlRsprDicCode.equals(other.getPrjDtlRsprDicCode()))) &&
            ((this.prjNm==null && other.getPrjNm()==null) || 
             (this.prjNm!=null &&
              this.prjNm.equals(other.getPrjNm()))) &&
            ((this.prjNo==null && other.getPrjNo()==null) || 
             (this.prjNo!=null &&
              this.prjNo.equals(other.getPrjNo()))) &&
            ((this.prjTypeNm==null && other.getPrjTypeNm()==null) || 
             (this.prjTypeNm!=null &&
              this.prjTypeNm.equals(other.getPrjTypeNm()))) &&
            ((this.prtcpMpDicCodeList==null && other.getPrtcpMpDicCodeList()==null) || 
             (this.prtcpMpDicCodeList!=null &&
              this.prtcpMpDicCodeList.equals(other.getPrtcpMpDicCodeList()))) &&
            ((this.prtcpMpList==null && other.getPrtcpMpList()==null) || 
             (this.prtcpMpList!=null &&
              this.prtcpMpList.equals(other.getPrtcpMpList()))) &&
            ((this.reaultFlag==null && other.getReaultFlag()==null) || 
             (this.reaultFlag!=null &&
              this.reaultFlag.equals(other.getReaultFlag()))) &&
            ((this.resultMsg==null && other.getResultMsg()==null) || 
             (this.resultMsg!=null &&
              this.resultMsg.equals(other.getResultMsg()))) &&
            ((this.rndPhaseNm==null && other.getRndPhaseNm()==null) || 
             (this.rndPhaseNm!=null &&
              this.rndPhaseNm.equals(other.getRndPhaseNm()))) &&
            ((this.rndcoTotAmt==null && other.getRndcoTotAmt()==null) || 
             (this.rndcoTotAmt!=null &&
              this.rndcoTotAmt.equals(other.getRndcoTotAmt()))) &&
            ((this.subRsprDeptNm==null && other.getSubRsprDeptNm()==null) || 
             (this.subRsprDeptNm!=null &&
              this.subRsprDeptNm.equals(other.getSubRsprDeptNm()))) &&
            ((this.subRsprNm==null && other.getSubRsprNm()==null) || 
             (this.subRsprNm!=null &&
              this.subRsprNm.equals(other.getSubRsprNm()))) &&
            ((this.subRsprTopDeptNm==null && other.getSubRsprTopDeptNm()==null) || 
             (this.subRsprTopDeptNm!=null &&
              this.subRsprTopDeptNm.equals(other.getSubRsprTopDeptNm()))) &&
            ((this.sysId==null && other.getSysId()==null) || 
             (this.sysId!=null &&
              this.sysId.equals(other.getSysId()))) &&
            ((this.sysPwd==null && other.getSysPwd()==null) || 
             (this.sysPwd!=null &&
              this.sysPwd.equals(other.getSysPwd()))) &&
            ((this.totRschEndDt==null && other.getTotRschEndDt()==null) || 
             (this.totRschEndDt!=null &&
              this.totRschEndDt.equals(other.getTotRschEndDt()))) &&
            ((this.totRschEndYear==null && other.getTotRschEndYear()==null) || 
             (this.totRschEndYear!=null &&
              this.totRschEndYear.equals(other.getTotRschEndYear()))) &&
            ((this.totRschStartDt==null && other.getTotRschStartDt()==null) || 
             (this.totRschStartDt!=null &&
              this.totRschStartDt.equals(other.getTotRschStartDt()))) &&
            ((this.totRschStartYear==null && other.getTotRschStartYear()==null) || 
             (this.totRschStartYear!=null &&
              this.totRschStartYear.equals(other.getTotRschStartYear()))) &&
            this.totalRow == other.getTotalRow() &&
            ((this.updateDt==null && other.getUpdateDt()==null) || 
             (this.updateDt!=null &&
              this.updateDt.equals(other.getUpdateDt()))) &&
            ((this.yearCnt==null && other.getYearCnt()==null) || 
             (this.yearCnt!=null &&
              this.yearCnt.equals(other.getYearCnt())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCountControl() != null) {
            _hashCode += getCountControl().hashCode();
        }
        _hashCode += getFindPage();
        if (getFindPrjDtlNm() != null) {
            _hashCode += getFindPrjDtlNm().hashCode();
        }
        if (getFindPrjDtlNo() != null) {
            _hashCode += getFindPrjDtlNo().hashCode();
        }
        if (getFindPrjDtlRsprDicCode() != null) {
            _hashCode += getFindPrjDtlRsprDicCode().hashCode();
        }
        if (getFindPrjDtlRsprNm() != null) {
            _hashCode += getFindPrjDtlRsprNm().hashCode();
        }
        if (getFindPrjDtlYear() != null) {
            _hashCode += getFindPrjDtlYear().hashCode();
        }
        if (getFindPrtcpMp() != null) {
            _hashCode += getFindPrtcpMp().hashCode();
        }
        if (getFindPrtcpMpDicCode() != null) {
            _hashCode += getFindPrtcpMpDicCode().hashCode();
        }
        _hashCode += getFindRowPerPage();
        if (getKorKywd() != null) {
            _hashCode += getKorKywd().hashCode();
        }
        if (getMainRsprDeptNm() != null) {
            _hashCode += getMainRsprDeptNm().hashCode();
        }
        if (getMainRsprNm() != null) {
            _hashCode += getMainRsprNm().hashCode();
        }
        if (getMainRsprTopDeptNm() != null) {
            _hashCode += getMainRsprTopDeptNm().hashCode();
        }
        _hashCode += getPageNo();
        if (getPrjDtlInfoList() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPrjDtlInfoList());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPrjDtlInfoList(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPrjDtlNm() != null) {
            _hashCode += getPrjDtlNm().hashCode();
        }
        if (getPrjDtlNo() != null) {
            _hashCode += getPrjDtlNo().hashCode();
        }
        if (getPrjDtlRsprDicCode() != null) {
            _hashCode += getPrjDtlRsprDicCode().hashCode();
        }
        if (getPrjNm() != null) {
            _hashCode += getPrjNm().hashCode();
        }
        if (getPrjNo() != null) {
            _hashCode += getPrjNo().hashCode();
        }
        if (getPrjTypeNm() != null) {
            _hashCode += getPrjTypeNm().hashCode();
        }
        if (getPrtcpMpDicCodeList() != null) {
            _hashCode += getPrtcpMpDicCodeList().hashCode();
        }
        if (getPrtcpMpList() != null) {
            _hashCode += getPrtcpMpList().hashCode();
        }
        if (getReaultFlag() != null) {
            _hashCode += getReaultFlag().hashCode();
        }
        if (getResultMsg() != null) {
            _hashCode += getResultMsg().hashCode();
        }
        if (getRndPhaseNm() != null) {
            _hashCode += getRndPhaseNm().hashCode();
        }
        if (getRndcoTotAmt() != null) {
            _hashCode += getRndcoTotAmt().hashCode();
        }
        if (getSubRsprDeptNm() != null) {
            _hashCode += getSubRsprDeptNm().hashCode();
        }
        if (getSubRsprNm() != null) {
            _hashCode += getSubRsprNm().hashCode();
        }
        if (getSubRsprTopDeptNm() != null) {
            _hashCode += getSubRsprTopDeptNm().hashCode();
        }
        if (getSysId() != null) {
            _hashCode += getSysId().hashCode();
        }
        if (getSysPwd() != null) {
            _hashCode += getSysPwd().hashCode();
        }
        if (getTotRschEndDt() != null) {
            _hashCode += getTotRschEndDt().hashCode();
        }
        if (getTotRschEndYear() != null) {
            _hashCode += getTotRschEndYear().hashCode();
        }
        if (getTotRschStartDt() != null) {
            _hashCode += getTotRschStartDt().hashCode();
        }
        if (getTotRschStartYear() != null) {
            _hashCode += getTotRschStartYear().hashCode();
        }
        _hashCode += getTotalRow();
        if (getUpdateDt() != null) {
            _hashCode += getUpdateDt().hashCode();
        }
        if (getYearCnt() != null) {
            _hashCode += getYearCnt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PrjDtlInfoVo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "PrjDtlInfoVo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countControl");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "countControl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrjDtlNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrjDtlNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrjDtlNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrjDtlNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrjDtlRsprDicCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrjDtlRsprDicCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrjDtlRsprNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrjDtlRsprNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrjDtlYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrjDtlYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrtcpMp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrtcpMp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findPrtcpMpDicCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findPrtcpMpDicCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("findRowPerPage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "findRowPerPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("korKywd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "korKywd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainRsprDeptNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "mainRsprDeptNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainRsprNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "mainRsprNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mainRsprTopDeptNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "mainRsprTopDeptNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "pageNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjDtlInfoList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjDtlInfoList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjDtlNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjDtlNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjDtlNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjDtlNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjDtlRsprDicCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjDtlRsprDicCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prjTypeNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prjTypeNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prtcpMpDicCodeList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prtcpMpDicCodeList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prtcpMpList");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "prtcpMpList"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reaultFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "reaultFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "resultMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rndPhaseNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "rndPhaseNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rndcoTotAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "rndcoTotAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subRsprDeptNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "subRsprDeptNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subRsprNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "subRsprNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subRsprTopDeptNm");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "subRsprTopDeptNm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "sysId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sysPwd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "sysPwd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRschEndDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "totRschEndDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRschEndYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "totRschEndYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRschStartDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "totRschStartDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totRschStartYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "totRschStartYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRow");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "totalRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "updateDt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearCnt");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "yearCnt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
