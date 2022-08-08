/**
 * PrjDtlInfoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.digitalLab.atis.prjdtlinfo;

public class PrjDtlInfoServiceLocator extends org.apache.axis.client.Service implements com.digitalLab.atis.prjdtlinfo.PrjDtlInfoService {

    public PrjDtlInfoServiceLocator() {
    }


    public PrjDtlInfoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PrjDtlInfoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PrjDtlInfo
    private java.lang.String PrjDtlInfo_address = "http://atis.rda.go.kr/rdais/services/PrjDtlInfo";

    public java.lang.String getPrjDtlInfoAddress() {
        return PrjDtlInfo_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PrjDtlInfoWSDDServiceName = "PrjDtlInfo";

    public java.lang.String getPrjDtlInfoWSDDServiceName() {
        return PrjDtlInfoWSDDServiceName;
    }

    public void setPrjDtlInfoWSDDServiceName(java.lang.String name) {
        PrjDtlInfoWSDDServiceName = name;
    }

    public com.digitalLab.atis.prjdtlinfo.PrjDtlInfo getPrjDtlInfo() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PrjDtlInfo_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPrjDtlInfo(endpoint);
    }

    public com.digitalLab.atis.prjdtlinfo.PrjDtlInfo getPrjDtlInfo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.digitalLab.atis.prjdtlinfo.PrjDtlInfoSoapBindingStub _stub = new com.digitalLab.atis.prjdtlinfo.PrjDtlInfoSoapBindingStub(portAddress, this);
            _stub.setPortName(getPrjDtlInfoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPrjDtlInfoEndpointAddress(java.lang.String address) {
        PrjDtlInfo_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.digitalLab.atis.prjdtlinfo.PrjDtlInfo.class.isAssignableFrom(serviceEndpointInterface)) {
                com.digitalLab.atis.prjdtlinfo.PrjDtlInfoSoapBindingStub _stub = new com.digitalLab.atis.prjdtlinfo.PrjDtlInfoSoapBindingStub(new java.net.URL(PrjDtlInfo_address), this);
                _stub.setPortName(getPrjDtlInfoWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PrjDtlInfo".equals(inputPortName)) {
            return getPrjDtlInfo();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "PrjDtlInfoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "PrjDtlInfo"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PrjDtlInfo".equals(portName)) {
            setPrjDtlInfoEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
