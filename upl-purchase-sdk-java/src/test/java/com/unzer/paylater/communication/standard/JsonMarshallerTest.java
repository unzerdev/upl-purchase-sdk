package com.unzer.paylater.communication.standard;

import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.model.Amount;
import com.unzer.paylater.model.Currency;
import com.unzer.paylater.model.PurchaseInformation;
import com.unzer.paylater.model.PurchaseOperationResponse;

public class JsonMarshallerTest {

    /**
     * Verify that unmarshalling newer versions of a class that has additional fields goes well.
     */
    @Test
    public void testUnmarshalWithExtraFields() {
        PurchaseOperationResponseWithExtraField original = new PurchaseOperationResponseWithExtraField();
        original.withPurchase(new PurchaseInformation()
                .withPurchaseAmount(new Amount()
                        .withAmount(100L)
                        .withCurrency(Currency.EUR)));

        original.extraField = "extra-field-value";

        String json = JsonMarshaller.INSTANCE.marshal(original);

        PurchaseOperationResponse unmarshalled = JsonMarshaller.INSTANCE.unmarshal(json, PurchaseOperationResponse.class);

        Assert.assertEquals(original.getPurchase(), unmarshalled.getPurchase());
    }

    static final class PurchaseOperationResponseWithExtraField extends PurchaseOperationResponse {

        private String extraField;

        public String getExtraField() {
            return extraField;
        }
        public void setExtraField(String extraField) {
            this.extraField = extraField;
        }
    }
}
