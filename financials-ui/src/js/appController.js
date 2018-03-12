/* global self, collection, ajax */

/**
 * Copyright (c) 2014, 2018, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your application specific code will go here
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojcollectiontabledatasource', 'ojs/ojmodel'],
        function (oj, ko, $) {
            function ControllerViewModel() {
                var self = this;

                // Media queries for repsonsive layouts
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);

                // Header
                // Application Name used in Branding Area
                self.appName = ko.observable("Financials area");
                // User Info used in Global Navigation area
                self.userLogin = ko.observable("anonymous");

                // Footer
                function footerLink(name, id, linkTarget) {
                    this.name = name;
                    this.linkId = id;
                    this.linkTarget = linkTarget;
                }
                self.footerLinks = ko.observableArray([
                    new footerLink('About Oracle', 'aboutOracle', 'http://www.oracle.com/us/corporate/index.html#menu-about'),
                    new footerLink('Contact Us', 'contactUs', 'http://www.oracle.com/us/corporate/contact/index.html'),
                    new footerLink('Legal Notices', 'legalNotices', 'http://www.oracle.com/us/legal/index.html'),
                    new footerLink('Terms Of Use', 'termsOfUse', 'http://www.oracle.com/us/legal/terms/index.html'),
                    new footerLink('Your Privacy Rights', 'yourPrivacyRights', 'http://www.oracle.com/us/legal/privacy/index.html')
                ]);

                var model = oj.Model.extend({
                    idAttribute: 'invoice_id'
                });

                var collection = new oj.Collection(null, {
                    url: "http://localhost:7777/api/financials/invoices",
                    fetchSize: 15,
                    model: model
                });

                function parseCustomerAccountInfo(account) {
                    
                    this.customer_no = account.customer_no;
                    this.first_name = account.first_name;
                    this.last_name = account.last_name;
                    this.account_balance = account.account_balance;
                    this.balance = account.balance;
                    this.currency = 'EUR';
                };

                self.getCustomerAccount = function () {
                    $.ajax({
                        type: 'GET',
                        url: "http://localhost:7777/api/financials/customers/CGN4712/account",
                        success: function (res) {
                            
                            self.customer_first_name = ko.observable(res.first_name);
                            self.customer_last_name = ko.observable(res.last_name);
                            self.customer_customer_no = ko.observable(res.customer_no);
                            self.customer_balance = ko.observable(res.balance);
                            self.customer_currency = 'EUR';
                            return res;
                        },
                        failure: function (textStatus, errorThrown) {
                            alert('Failed to load invoices' + textStatus);
                            return false;
                        }
                    });
                };

                var customer = new self.getCustomerAccount();
                self.customerAccount = ko.observable(customer);
                
                self.collection = collection;
                self.dataSource = ko.observableArray(collection);
                self.dataSource(new oj.CollectionTableDataSource(collection));

                this.selectedItems = ko.observableArray([]);

                this.showSelectedItem = function () {
                    $each(self.dataSource(), function (index, value) {
                        console.log('SELECTED ITEM: ' + value);
                    });
                };
            }

            return new ControllerViewModel();
        }
);
