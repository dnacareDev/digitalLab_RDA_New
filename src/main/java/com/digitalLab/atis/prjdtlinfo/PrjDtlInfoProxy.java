package com.digitalLab.atis.prjdtlinfo;

public class PrjDtlInfoProxy implements com.digitalLab.atis.prjdtlinfo.PrjDtlInfo {
  private String _endpoint = null;
  private com.digitalLab.atis.prjdtlinfo.PrjDtlInfo prjDtlInfo = null;
  
  public PrjDtlInfoProxy() {
    _initPrjDtlInfoProxy();
  }
  
  public PrjDtlInfoProxy(String endpoint) {
    _endpoint = endpoint;
    _initPrjDtlInfoProxy();
  }
  
  private void _initPrjDtlInfoProxy() {
    try {
      prjDtlInfo = (new com.digitalLab.atis.prjdtlinfo.PrjDtlInfoServiceLocator()).getPrjDtlInfo();
      if (prjDtlInfo != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)prjDtlInfo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)prjDtlInfo)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (prjDtlInfo != null)
      ((javax.xml.rpc.Stub)prjDtlInfo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.digitalLab.atis.prjdtlinfo.PrjDtlInfo getPrjDtlInfo() {
    if (prjDtlInfo == null)
      _initPrjDtlInfoProxy();
    return prjDtlInfo;
  }
  
  public com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo getPrjDtlInfo(com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo vo) throws java.rmi.RemoteException{
    if (prjDtlInfo == null)
      _initPrjDtlInfoProxy();
    return prjDtlInfo.getPrjDtlInfo(vo);
  }
  
  
}