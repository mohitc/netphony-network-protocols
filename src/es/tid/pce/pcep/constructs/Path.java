package es.tid.pce.pcep.constructs;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.pce.pcep.objects.BitmapLabelSet;
import es.tid.pce.pcep.objects.ExplicitRouteObject;
import es.tid.pce.pcep.objects.IncludeRouteObject;
import es.tid.pce.pcep.objects.InterLayer;
import es.tid.pce.pcep.objects.LSPA;
import es.tid.pce.pcep.objects.LabelSet;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Metric;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.ObjectiveFunction;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.ReqAdapCap;
import es.tid.pce.pcep.objects.SRERO;
import es.tid.pce.pcep.objects.ServerIndication;
import es.tid.pce.pcep.objects.SuggestedLabel;
import es.tid.pce.pcep.objects.SwitchLayer;

/**
 * Path PCEP Construct. RFC 5440
 * From RFC 5440 Section 6.5
 *      <path>::= <ERO><attribute-list>

   where:

    <attribute-list>::= [OF]
    					[<LSPA>]
                       [<BANDWIDTH>]
                       [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                       [<metric-list>]
                       [<IRO>]
  						[<INTER-LAYER>]
                            [<SWITCH-LAYER>]
                            [<REQ-ADAP-CAP>]
                            [<SERVER-INDICATION>]
                            
    <metric-list>::=<METRIC>[<metric-list>]
 * @author ogondio
 *
 */
public class Path extends PCEPConstruct {

	private ExplicitRouteObject eRO;
	private SRERO SRERO;
	private ObjectiveFunction of;
	private LSPA lSPA;
	private Bandwidth bandwidth;
	private LinkedList<Metric> metricList;
	private IncludeRouteObject iRO;
	private InterLayer interLayer;
	private SwitchLayer switchLayer;
	private ReqAdapCap reqAdapCap;
	private ServerIndication serverIndication;
	
	private LabelSet labelSet;
	
	private SuggestedLabel suggestedLabel;
	
	
	public Path(){
		metricList=new LinkedList<Metric>();
	}
	
	public Path(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}
	
	public void encode() throws PCEPProtocolViolationException {

		//Encoding Request Rule
		int len=0;
		if (eRO!=null){
			eRO.encode();
			len=len+eRO.getLength();
		}
		else if (SRERO!=null){
			SRERO.encode();
			len+=SRERO.getLength();
		}
		else {
			log.warn("Path must start with ERO or SRERO object");
			throw new PCEPProtocolViolationException();
		}
		if (of!=null){
			of.encode();
			len=len+of.getLength();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			(metricList.get(i)).encode();
			len=len+(metricList.get(i)).getLength();
		}
		if (iRO!=null){
			iRO.encode();
			len=len+iRO.getLength();
		}
		if (interLayer!=null){
			interLayer.encode();
			len=len+interLayer.getLength();
		}
		if (switchLayer!=null){
			switchLayer.encode();
			len=len+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			reqAdapCap.encode();
			len=len+reqAdapCap.getLength();
		}
		if (serverIndication!=null){
			serverIndication.encode();
			len=len+serverIndication.getLength();
		}
		if (labelSet!=null) {
			labelSet.encode();
			len=len+labelSet.getLength();
		}
		if (suggestedLabel!=null) {
			suggestedLabel.encode();
			len=len+suggestedLabel.getLength();
		}
		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		if(eRO!=null)
		{
			System.arraycopy(eRO.getBytes(), 0, bytes, offset, eRO.getLength());
			offset=offset+eRO.getLength();
		}
		else if(SRERO!=null)
		{
			System.arraycopy(SRERO.getBytes(), 0, bytes, offset, SRERO.getLength());
			offset=offset+SRERO.getLength();			
		}	
		if (of!=null){
			System.arraycopy(of.getBytes(), 0, bytes, offset, of.getLength());
			offset=offset+of.getLength();
		}	
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();
		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset=offset+metricList.get(i).getLength();
		}
		if (iRO!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, iRO.getLength());
			offset=offset+iRO.getLength();
		}
		
		if (interLayer!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, interLayer.getLength());
			offset=offset+interLayer.getLength();
		}
		if (switchLayer!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, switchLayer.getLength());
			offset=offset+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, reqAdapCap.getLength());
			offset=offset+reqAdapCap.getLength();
		}
		if (serverIndication!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, serverIndication.getLength());
			offset=offset+serverIndication.getLength();
		}
		if (labelSet!=null) {
			System.arraycopy(labelSet.getBytes(), 0, bytes, offset, labelSet.getLength());
			offset=offset+labelSet.getLength();
		}
		if (suggestedLabel!=null) {
			System.arraycopy(suggestedLabel.getBytes(), 0, bytes, offset, suggestedLabel.getLength());
			offset=offset+suggestedLabel.getLength();
		}
			
	}

	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		//Decoding Path Rule
		int len=0;		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			try {
				eRO=new ExplicitRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}
			offset=offset+eRO.getLength();
			len=len+eRO.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO){
			try {
				SRERO=new SRERO(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				throw new PCEPProtocolViolationException();
			}
			offset=offset+SRERO.getLength();
			len=len+SRERO.getLength();
		}		
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION){
			try {
				of=new ObjectiveFunction(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed OF Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+of.getLength();
			len=len+of.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			try {
				lSPA=new LSPA(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lSPA.getLength();
			len=len+lSPA.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}			
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthExistingLSP(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST){
				try {
					bandwidth=new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else {
				log.warn("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_METRIC){
			Metric metric;
			try {
				metric = new Metric(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset=offset+metric.getLength();
			len=len+metric.getLength();
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_IRO){
			try {
				iRO=new IncludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed IRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+iRO.getLength();
			len=len+iRO.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_INTER_LAYER){
			try {
				interLayer=new InterLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed interLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+interLayer.getLength();
			len=len+interLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SWITCH_LAYER){
			try {
				switchLayer=new SwitchLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed switchLayer Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+switchLayer.getLength();
			len=len+switchLayer.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_REQ_ADAP_CAP){
			try {
				reqAdapCap=new ReqAdapCap(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed reqAdapCap Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+reqAdapCap.getLength();
			len=len+reqAdapCap.getLength();
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SERVER_INDICATION){
			try {
				serverIndication=new ServerIndication(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed serverIndication Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+serverIndication.getLength();
			len=len+serverIndication.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);	
		ot=PCEPObject.getObjectType(bytes, offset);
	
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LABEL_SET){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_LABEL_SET_BITMAP){
				try {
					labelSet=new BitmapLabelSet(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed Suggested Label Object found");
					throw new PCEPProtocolViolationException();
				}
				offset=offset+labelSet.getLength();
				len=len+labelSet.getLength();
			}
			
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SUGGESTED_LABEL){
			try {
				suggestedLabel=new SuggestedLabel(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed Suggested Label Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+suggestedLabel.getLength();
			len=len+suggestedLabel.getLength();
		}
		
		this.setLength(len);
	}
	
	
	public void setSRERO(SRERO srero)
	{
		this.SRERO = srero;
	}
	
	public void seteRO(ExplicitRouteObject eRO) {
		this.eRO = eRO;
	}
	
	public void setiRO(IncludeRouteObject iRO) {
		this.iRO = iRO;
	}

	public void setlSPA(LSPA lSPA) {
		this.lSPA = lSPA;
	}
	
	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}
	
	
	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public IncludeRouteObject getiRO() {
		return iRO;
	}

	public Bandwidth getBandwidth() {
		return bandwidth;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}
	
	public LSPA getlSPA() {
		return lSPA;
	}

	public ExplicitRouteObject geteRO() {
		return eRO;
	}
	
	public SRERO getSRERO() {
		return this.SRERO;
	}
	
	public InterLayer getInterLayer() {
		return interLayer;
	}

	public void setInterLayer(InterLayer interLayer) {
		this.interLayer = interLayer;
	}

	public SwitchLayer getSwitchLayer() {
		return switchLayer;
	}

	public void setSwitchLayer(SwitchLayer switchLayer) {
		this.switchLayer = switchLayer;
	}

	public ReqAdapCap getReqAdapCap() {
		return reqAdapCap;
	}

	public void setReqAdapCap(ReqAdapCap reqAdapCap) {
		this.reqAdapCap = reqAdapCap;
	}

	public ServerIndication getServerIndication() {
		return serverIndication;
	}

	public void setServerIndication(ServerIndication serverIndication) {
		this.serverIndication = serverIndication;
	}

	public LabelSet getLabelSet() {
		return labelSet;
	}

	public void setLabelSet(LabelSet labelSet) {
		this.labelSet = labelSet;
	}

	public SuggestedLabel getSuggestedLabel() {
		return suggestedLabel;
	}

	public void setSuggestedLabel(SuggestedLabel suggestedLabel) {
		this.suggestedLabel = suggestedLabel;
	}
	
	

	
	public ObjectiveFunction getOf() {
		return of;
	}

	public void setOf(ObjectiveFunction of) {
		this.of = of;
	}

	public String toString(){
		String ret="PATH={ ";
		if (SRERO!=null){
			ret+=SRERO.toString();
		}
		if (eRO!=null){
			ret=ret+eRO.toString();
		}
		if (of!=null){
			ret=ret+of.toString();
		}
		if (lSPA!=null){
			ret=ret+lSPA.toString();
		}
		if (bandwidth!=null){
			ret=ret+bandwidth.toString();
		}
		if (serverIndication!=null){
			ret=ret+serverIndication.toString();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				ret=ret+metricList.get(i).toString();			}
		}
		if (iRO!=null){
			ret=ret+iRO.toString();
		}
		if (labelSet!=null) {
			ret=ret+labelSet.toString();
		}
		if (suggestedLabel!=null) {
			ret=ret+suggestedLabel.toString();
		}
		ret=ret+" }";

		return ret;
	}
}
