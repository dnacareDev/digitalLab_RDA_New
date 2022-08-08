/**
 * GetPrjDtlInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.digitalLab.atis.prjdtlinfo;

public class GetPrjDtlInfoResponse  implements java.io.Serializable {
    private com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo getPrjDtlInfoReturn;

    public GetPrjDtlInfoResponse() {
    }

    public GetPrjDtlInfoResponse(
           com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo getPrjDtlInfoReturn) {
           this.getPrjDtlInfoReturn = getPrjDtlInfoReturn;
    }


    /**
     * Gets the getPrjDtlInfoReturn value for this GetPrjDtlInfoResponse.
     * 
     * @return getPrjDtlInfoReturn
     */
    public com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo getGetPrjDtlInfoReturn() {
        return getPrjDtlInfoReturn;
    }


    /**
     * Sets the getPrjDtlInfoReturn value for this GetPrjDtlInfoResponse.
     * 
     * @param getPrjDtlInfoReturn
     */
    public void setGetPrjDtlInfoReturn(com.digitalLab.atis.prjdtlinfo.PrjDtlInfoVo getPrjDtlInfoReturn) {
        this.getPrjDtlInfoReturn = getPrjDtlInfoReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPrjDtlInfoResponse)) return false;
        GetPrjDtlInfoResponse other = (GetPrjDtlInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getPrjDtlInfoReturn==null && other.getGetPrjDtlInfoReturn()==null) || 
             (this.getPrjDtlInfoReturn!=null &&
              this.getPrjDtlInfoReturn.equals(other.getGetPrjDtlInfoReturn())));
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
        if (getGetPrjDtlInfoReturn() != null) {
            _hashCode += getGetPrjDtlInfoReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPrjDtlInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", ">getPrjDtlInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getPrjDtlInfoReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "getPrjDtlInfoReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://prjdtlinfo.prj.wsdl.atis.rda.go.kr", "PrjDtlInfoVo"));
        elemField.setNillable(false);
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
