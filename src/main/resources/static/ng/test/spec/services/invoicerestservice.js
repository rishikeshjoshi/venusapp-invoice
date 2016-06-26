'use strict';

describe('Service: InvoiceRestService', function () {

  // load the service's module
  beforeEach(module('ngInvoiceApp'));

  // instantiate service
  var InvoiceRestService;
  beforeEach(inject(function (_InvoiceRestService_) {
    InvoiceRestService = _InvoiceRestService_;
  }));

  it('should do something', function () {
    expect(!!InvoiceRestService).toBe(true);
  });

});
