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
                    console.log("Financials - New global context listener is registered");
                    self.globalContextListeners.push(listener);
                    if (self.globalContext){
                        console.log('Financials - GLobalContext already available! Sending it to the Listener directly...');
                        listener(self.globalContext);
                    }
                };

                self.callParent = function (message) {
                    console.log('send message from Invoice to parent window');
                    var targetOrigin = '*';
                    parent.postMessage(message, targetOrigin);
                };

                self.init = function () {

                    window.addEventListener("message", function (event) {
                        console.log("Financials - Received message from embedding application " + event);
                        console.log("Financials - Payload =  " + JSON.stringify(event.data));
                        if (event.data.eventType === "globalContext") {
                            self.globalContext = event.data.payload.globalContext;
                            //inform listeners of new global context
                            console.log("Financials - Inform all listeners about the globalContext");
                            self.globalContextListeners.forEach(function (listener) {
                                console.log("Financials - Send globalContext to all listeners... " + listener);
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