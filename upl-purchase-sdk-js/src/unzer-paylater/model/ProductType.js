/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */

const ValidationException = require("../exceptions/ValidationException");

module.exports = Object.freeze({
    INVOICE: "INVOICE",
    INSTALLMENT: "INSTALLMENT",
    MONTHLY_INVOICE: "MONTHLY_INVOICE",
    constructFromObject: function (data) {
        if (data === null || data === undefined || this.hasOwnProperty(data)) {
            return data;
        }
        throw new ValidationException(`Could not find '${data}' in String.`);
    },
});
