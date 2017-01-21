/*
 * Copyright (c) 2010 - 2015 Norwegian Agency for Pupblic Government and eGovernment (Difi)
 *
 * This file is part of Oxalis.
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission
 * - subsequent versions of the EUPL (the "Licence"); You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the Licence
 *  is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and limitations under the Licence.
 *
 */

package eu.peppol.as2.outbound;

import eu.peppol.identifier.MessageId;
import no.difi.oxalis.api.outbound.TransmissionRequest;
import no.difi.oxalis.api.outbound.TransmissionResponse;
import no.difi.vefa.peppol.common.model.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Implementation of {@link TransmissionResponse} for use in AS2.
 *
 * @author steinar
 * @author thore
 * @author erlend
 */
class As2TransmissionResponse implements TransmissionResponse, Serializable {

    private static final long serialVersionUID = 4288900204693153668L;

    /**
     * Original transmission request is kept to allow easy access to immutable objects part of the request.
     */
    private final TransmissionRequest transmissionRequest;

    private final MessageId messageId;

    private final Receipt receipt;

    private final Date timestamp;

    public As2TransmissionResponse(TransmissionRequest transmissionRequest, byte[] nativeEvidenceBytes,
                                   Date timestamp) {
        this.transmissionRequest = transmissionRequest;
        this.messageId = transmissionRequest.getMessageId();
        this.receipt = Receipt.of("message/disposition-notification", nativeEvidenceBytes);
        this.timestamp = timestamp;
    }

    @Override
    public Header getHeader() {
        return transmissionRequest.getHeader();
    }

    @Override
    public MessageId getMessageId() {
        return messageId;
    }

    @Override
    public List<Receipt> getReceipts() {
        return Collections.singletonList(receipt);
    }

    @Override
    public Endpoint getEndpoint() {
        return transmissionRequest.getEndpoint();
    }

    @Override
    public Receipt primaryReceipt() {
        return receipt;
    }

    @Override
    public Digest getDigest() {
        return null;
    }

    @Override
    public TransportProtocol getTransportProtocol() {
        return TransportProtocol.AS2;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}