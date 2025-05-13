package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.PaymentMethod;
import com.heavenscode.rac.repository.PaymentMethodRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.PaymentMethod}.
 */
@Service
@Transactional
public class PaymentMethodService {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentMethodService.class);

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    /**
     * Save a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    public PaymentMethod save(PaymentMethod paymentMethod) {
        LOG.debug("Request to save PaymentMethod : {}", paymentMethod);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Update a paymentMethod.
     *
     * @param paymentMethod the entity to save.
     * @return the persisted entity.
     */
    public PaymentMethod update(PaymentMethod paymentMethod) {
        LOG.debug("Request to update PaymentMethod : {}", paymentMethod);
        return paymentMethodRepository.save(paymentMethod);
    }

    /**
     * Partially update a paymentMethod.
     *
     * @param paymentMethod the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentMethod> partialUpdate(PaymentMethod paymentMethod) {
        LOG.debug("Request to partially update PaymentMethod : {}", paymentMethod);

        return paymentMethodRepository
            .findById(paymentMethod.getId())
            .map(existingPaymentMethod -> {
                if (paymentMethod.getPaymentMethodName() != null) {
                    existingPaymentMethod.setPaymentMethodName(paymentMethod.getPaymentMethodName());
                }
                if (paymentMethod.getCommission() != null) {
                    existingPaymentMethod.setCommission(paymentMethod.getCommission());
                }
                if (paymentMethod.getCompanyBankAccountId() != null) {
                    existingPaymentMethod.setCompanyBankAccountId(paymentMethod.getCompanyBankAccountId());
                }
                if (paymentMethod.getLmd() != null) {
                    existingPaymentMethod.setLmd(paymentMethod.getLmd());
                }
                if (paymentMethod.getLmu() != null) {
                    existingPaymentMethod.setLmu(paymentMethod.getLmu());
                }

                return existingPaymentMethod;
            })
            .map(paymentMethodRepository::save);
    }

    /**
     * Get one paymentMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> findOne(Long id) {
        LOG.debug("Request to get PaymentMethod : {}", id);
        return paymentMethodRepository.findById(id);
    }

    /**
     * Delete the paymentMethod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete PaymentMethod : {}", id);
        paymentMethodRepository.deleteById(id);
    }
}
