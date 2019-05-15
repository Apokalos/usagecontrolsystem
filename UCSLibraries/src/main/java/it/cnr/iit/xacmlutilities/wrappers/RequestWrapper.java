package it.cnr.iit.xacmlutilities.wrappers;

import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import it.cnr.iit.utility.JAXBUtility;
import it.cnr.iit.utility.errorhandling.Reject;

import oasis.names.tc.xacml.core.schema.wd_17.RequestType;

public class RequestWrapper {
    private static final Logger log = Logger.getLogger( RequestWrapper.class.getName() );

    private static final String MSG_ERR_UNMASHAL = "Error unmarshalling request : {0}";
    private static final String MSG_ERR_MARSHAL = "Error marshalling request : {0}";

    private RequestType requestType;
    private String request;

    private RequestWrapper() {}

    // TODO use optional
    public static RequestWrapper buildRequestWrapper( String request ) {
        Reject.ifBlank( request );

        RequestWrapper requestHelper = new RequestWrapper();
        requestHelper.setRequest( request );

        return requestHelper.requestType != null ? requestHelper : null;
    }

    public static RequestType unmarshalRequestType( String request ) {
        try {
            return JAXBUtility.unmarshalToObject( RequestType.class, request );
        } catch( Exception e ) {
            log.severe( String.format( MSG_ERR_UNMASHAL, e.getMessage() ) );
        }
        return null;
    }

    public static String marshalRequestType( RequestType request ) {
        try {
            return JAXBUtility.marshalToString( RequestType.class, request, "Request",
                JAXBUtility.SCHEMA );
        } catch( JAXBException e ) {
            log.severe( String.format( MSG_ERR_MARSHAL, e.getMessage() ) );
        }
        return null;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest( String request ) {
        this.request = request;
        this.requestType = unmarshalRequestType( request );
    }

    public RequestType getRequestType() {
        return requestType;
    }

}
