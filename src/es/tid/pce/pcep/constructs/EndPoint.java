package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.EndPointApplicationTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointIPv4TLV;
import es.tid.pce.pcep.objects.tlvs.EndPointServerTLV;
import es.tid.pce.pcep.objects.tlvs.EndPointStorageTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.UnnumberedEndpointTLV;
import es.tid.pce.pcep.objects.tlvs.XifiEndPointTLV;

public class EndPoint extends PCEPConstruct {
		
	private EndPointIPv4TLV endPointIPv4;
	
	private UnnumberedEndpointTLV unnumberedEndpoint;
	
	private EndPointStorageTLV endPointStorage;
	
	private EndPointServerTLV endPointServer;
	
	private EndPointApplicationTLV endPointApplication;
	
	private XifiEndPointTLV xifiEndPointTLV;
	
	public EndPoint() {
		
	}

	
	public EndPoint(byte[] bytes, int offset)throws MalformedPCEPObjectException {
		decode(bytes,offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		// TODO Auto-generated method stub
		int length=0;
		
		if (endPointIPv4!=null){
			endPointIPv4.encode();
			length=length+endPointIPv4.getTotalTLVLength();
		}
		if (unnumberedEndpoint!=null){
			unnumberedEndpoint.encode();
			length=length+unnumberedEndpoint.getTotalTLVLength();
		}
		
		if (endPointStorage!=null){
			endPointStorage.encode();
			length=length+endPointStorage.getTotalTLVLength();
		}
		
		if (endPointServer!=null){
			endPointServer.encode();
			length=length+endPointServer.getTotalTLVLength();
		}
		
		if (endPointApplication!=null){
			endPointApplication.encode();
			length=length+endPointApplication.getTotalTLVLength();
		}
		
		if (xifiEndPointTLV != null)
		{
			xifiEndPointTLV.encode();
			length = length + xifiEndPointTLV.getTotalTLVLength();
		}
		this.setLength(length);
		this.bytes=new byte[this.getLength()];
		int offset=0;
		
		if (endPointIPv4!=null){
			System.arraycopy(endPointIPv4.getTlv_bytes(),0,this.bytes,offset,endPointIPv4.getTotalTLVLength());
			offset=offset+endPointIPv4.getTotalTLVLength();
		}
		
		else if (unnumberedEndpoint!=null){
			System.arraycopy(unnumberedEndpoint.getTlv_bytes(),0,this.bytes,offset,unnumberedEndpoint.getTotalTLVLength());
			offset=offset+unnumberedEndpoint.getTotalTLVLength();
		}
		
		else if (endPointStorage!=null){
			System.arraycopy(endPointStorage.getTlv_bytes(),0,this.bytes,offset,endPointStorage.getTotalTLVLength());
			offset=offset+endPointStorage.getTotalTLVLength();
		}
		
		else if (endPointServer!=null){
			System.arraycopy(endPointServer.getTlv_bytes(),0,this.bytes,offset,endPointServer.getTotalTLVLength());
			offset=offset+endPointServer.getTotalTLVLength();
		}
		
		else if (endPointApplication!=null){
			System.arraycopy(endPointApplication.getTlv_bytes(),0,this.bytes,offset,endPointApplication.getTotalTLVLength());
			offset=offset+endPointApplication.getTotalTLVLength();
		}
		
		else if (xifiEndPointTLV != null)
		{
			System.arraycopy(xifiEndPointTLV.getTlv_bytes(),0,this.bytes,offset,xifiEndPointTLV.getTotalTLVLength());
			offset = offset + xifiEndPointTLV.getTotalTLVLength();
		}

	}
	public void decode(byte[] bytes, int offset) throws MalformedPCEPObjectException {		
		int tlvtype=PCEPTLV.getType(bytes, offset);
		int tlvlength=PCEPTLV.getTotalTLVLength(bytes, offset);
		this.setLength(tlvlength);

		if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_IPV4_ADDRESS){
			endPointIPv4=new EndPointIPv4TLV(bytes, offset);
		}
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT){
			unnumberedEndpoint=new UnnumberedEndpointTLV(bytes, offset);
		}
		
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_STORAGE){
			endPointStorage=new EndPointStorageTLV(bytes, offset);
		}
		
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_SERVER){
			endPointServer=new EndPointServerTLV(bytes, offset);
		}
		
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_APPLICATION){
			endPointApplication=new EndPointApplicationTLV(bytes, offset);
		}
		
		else if (tlvtype==ObjectParameters.PCEP_TLV_TYPE_XIFI)
		{
			xifiEndPointTLV = new XifiEndPointTLV(bytes, offset);
		}
	}
	
	public EndPointIPv4TLV getEndPointIPv4TLV() {
		return endPointIPv4;
	}

	public void setEndPointIPv4TLV(EndPointIPv4TLV EndPointIPv4TLV) {
		this.endPointIPv4 = EndPointIPv4TLV;
	}
	
	public EndPointStorageTLV getEndPointStorageTLV() {
		return endPointStorage;
	}

	public void setEndPointStorageTLV(EndPointStorageTLV EndPointStorageTLV) {
		this.endPointStorage = EndPointStorageTLV;
	}

	public EndPointServerTLV getEndPointServerTLV() {
		return endPointServer;
	}

	public void setEndPointServerTLV(EndPointServerTLV EndPointServerTLV) {
		this.endPointServer = EndPointServerTLV;
	}

	public EndPointApplicationTLV getEndPointApplicationTLV() {
		return endPointApplication;
	}

	public void setEndPointApplicationTLV(EndPointApplicationTLV EndPointApplicationTLV) {
		this.endPointApplication = EndPointApplicationTLV;
	}


	public XifiEndPointTLV getXifiEndPointTLV() 
	{
		return xifiEndPointTLV;
	}

	public void setXifiEndPointTLV(XifiEndPointTLV xifiEndPointTLV) 
	{
		this.xifiEndPointTLV = xifiEndPointTLV;
	}


	public UnnumberedEndpointTLV getUnnumberedEndpoint() {
		return unnumberedEndpoint;
	}


	public void setUnnumberedEndpoint(UnnumberedEndpointTLV unnumberedEndpoint) {
		this.unnumberedEndpoint = unnumberedEndpoint;
	}
	
	
	public String toString(){
		if (endPointIPv4!=null){
			return endPointIPv4.getIPv4address().getHostAddress();
		}else if (unnumberedEndpoint != null ){
			return unnumberedEndpoint.getIPv4address().getHostAddress()+":"+unnumberedEndpoint.getIfID();
		}else {
			return "";
		}
	}
	

}