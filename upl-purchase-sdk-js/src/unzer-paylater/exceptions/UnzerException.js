const ApiException = require("./ApiException");

class UnzerException extends ApiException {
  constructor(...params) {
    super(...params)
    this.name = 'UnzerException'
  }
}

module.exports = UnzerException;