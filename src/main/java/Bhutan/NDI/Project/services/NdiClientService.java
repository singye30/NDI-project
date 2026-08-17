package Bhutan.NDI.Project.services;

import Bhutan.NDI.Project.entity.NdiClientDetail;
import Bhutan.NDI.Project.repository.NdiClientDetailRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NdiClientService {

    private final NdiClientDetailRepository clientDetailRepository;

    public NdiClientService(NdiClientDetailRepository clientDetailRepository) {
        this.clientDetailRepository = clientDetailRepository;
    }

    /**
     * Get client by clientId (only active clients)
     */
    public Optional<NdiClientDetail> getActiveClient(String clientId) {
        return clientDetailRepository.findByClientIdAndClientStatus(clientId, "ACTIVE");
    }

    /**
     * Get client by clientId (any status)
     */
    public Optional<NdiClientDetail> getClient(String clientId) {
        return clientDetailRepository.findByClientId(clientId);
    }

    /**
     * Get redirect URL for a client
     */
    public String getClientRedirectUrl(String clientId) {
        Optional<NdiClientDetail> client = getActiveClient(clientId);
        return client.map(NdiClientDetail::getClientRedirectUrl).orElse(null);
    }

    /**
     * Create new client
     */
    public NdiClientDetail createClient(
            String clientId,
            String clientName,
            String clientRedirectUrl) {

        NdiClientDetail client = new NdiClientDetail();
        client.setClientId(clientId);
        client.setClientName(clientName);
        client.setClientRedirectUrl(clientRedirectUrl);
        client.setClientStatus("ACTIVE");

        return clientDetailRepository.save(client);
    }

    /**
     * Update client
     */
    public NdiClientDetail updateClient(
            String clientId,
            String clientName,
            String clientRedirectUrl) {

        Optional<NdiClientDetail> clientOpt = clientDetailRepository.findByClientId(clientId);

        if (clientOpt.isPresent()) {
            NdiClientDetail client = clientOpt.get();
            client.setClientName(clientName);
            client.setClientRedirectUrl(clientRedirectUrl);
            return clientDetailRepository.save(client);
        }

        return null;
    }

    /**
     * Deactivate client
     */
    public void deactivateClient(String clientId) {
        Optional<NdiClientDetail> clientOpt = clientDetailRepository.findByClientId(clientId);
        if (clientOpt.isPresent()) {
            NdiClientDetail client = clientOpt.get();
            client.setClientStatus("INACTIVE");
            clientDetailRepository.save(client);
        }
    }
}
