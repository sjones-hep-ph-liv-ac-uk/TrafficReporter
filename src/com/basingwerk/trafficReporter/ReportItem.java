package com.basingwerk.trafficReporter;

public class ReportItem {
    public enum protoType {
        TCP,
        UDP
    }
    public ReportItem() {
        this.sourceIp = null;
        this.destIp = null;
        this.tcpOrUdp = null;
        this.sourcePort = null;
        this.destPort = null;
    }
    public Boolean isComplete() {
        if ((this.sourceIp != null) && (this.destIp != null) && (this.tcpOrUdp != null) && (this.sourcePort != null) && (this.destPort != null)) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "ReportItem [sourceIp=" + sourceIp + ", destIp=" + destIp + ", tcpOrUdp=" + tcpOrUdp + ", sourcePort="
                + sourcePort + ", destPort=" + destPort + "]";
    }
    public String getSourceIp() {
        return sourceIp;
    }
    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
    public String getDestIp() {
        return destIp;
    }
    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }
    public protoType getTcpOrUdp() {
        return tcpOrUdp;
    }
    public void setTcpOrUdp(protoType tcpOrUdp) {
        this.tcpOrUdp = tcpOrUdp;
    }
    public String getSourcePort() {
        return sourcePort;
    }
    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }
    public String getDestPort() {
        return destPort;
    }
    public void setDestPort(String destPort) {
        this.destPort = destPort;
    }
    private String sourceIp; 
    private String destIp;
    private protoType tcpOrUdp;  
    private String sourcePort;
    private String destPort;
     

}
