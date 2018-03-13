define(['ojs/ojcore', 'knockout', 'ojs/ojknockout', 'ojs/ojrouter'],
        function (oj, ko) {
            function ControllerViewModel() {
                var self = this;

                self.globalContext = {};

                self.invoices = ko.observable("invoices");
                // Media queries for repsonsive layouts
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                
                self.appName = ko.observable("Financials area");

                self.globalContextListeners = [];
                self.registerGlobalContextListener = function (listener) {
                    console.log("New global context listener is registered");
                    self.globalContextListeners.push(listener);
                };

                self.callParent = function (message) {
                    console.log('send message from Invoice to parent window');
                    var targetOrigin = '*';
                    parent.postMessage(message, targetOrigin);
                };

                self.init = function () {
                    window.addEventListener("message", function (event) {
                        console.log("Received message from embedding application " + event);
                        console.log("Payload =  " + JSON.stringify(event.data));
                        if (event.data.eventType === "globalContext") {
                            self.globalContext = event.data.payload.globalContext;
                            //inform listeners of new global context
                            console.log("Inform all listeners about the globalContext");
                            self.globalContextListeners.forEach(function (listener) {
                                listener(self.globalContext);
                            });
                        }
                    },
                            false);
                    self.callParent({"childHasLoaded": true});
                };

                $(document).ready(function () {
                    self.init();
                });
            }

            return new ControllerViewModel();
        }
);