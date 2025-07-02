define(["postmonger"], function (Postmonger) {
    "use strict";

    var connection = new Postmonger.Session();
    var payload = {};
    var lastStepEnabled = false;
    var eventDefinitionKey;
    var schema;
    var selectElement = document.getElementById("select1");
    var steps = [
        // initialize to the same value as what's set in config.json for consistency
        { label: "Step 1", key: "step1" },
        { label: "Step 2", key: "step2" },
        { label: "Step 3", key: "step3" },
        { label: "Step 4", key: "step4", active: false },
    ];
    var currentStep = steps[0].key;

    $(window).ready(onRender);

    connection.on("initActivity", initialize);
    connection.on('requestedSchema', requestSch);
    connection.on('requestedTriggerEventDefinition', requestTriggerEvent);
    connection.on("requestedTokens", onGetTokens);
    connection.on("requestedEndpoints", onGetEndpoints);

    connection.on("clickedNext", onClickedNext);
    connection.on("clickedBack", onClickedBack);
    connection.on("gotoStep", onGotoStep);

    function onRender() {
        // JB will respond the first time 'ready' is called with 'initActivity'
        connection.trigger("ready");

        connection.trigger('requestSchema');
        connection.trigger('requestTriggerEventDefinition');
        connection.trigger("requestTokens");
        connection.trigger("requestEndpoints");

        // Disable the next button if a value isn't selected
        $("#select1").change(function () {
            var message = getMessage();
            connection.trigger("updateButton", {
                button: "next",
                enabled: Boolean(message),
            });

            $("#message").html(message);
        });

        // Toggle step 4 active/inactive
        // If inactive, wizard hides it and skips over it during navigation
        $("#toggleLastStep").click(function () {
            lastStepEnabled = !lastStepEnabled; // toggle status
            steps[3].active = !steps[3].active; // toggle active

            connection.trigger("updateSteps", steps);
        });
    }

    function initialize(data) {
        if (data) {
            payload = data;
        }

        var message;
        var hasInArguments = Boolean(
            payload["arguments"] &&
            payload["arguments"].execute &&
            payload["arguments"].execute.inArguments &&
            payload["arguments"].execute.inArguments.length > 0
        );

        var inArguments = hasInArguments
            ? payload["arguments"].execute.inArguments
            : {};
        console.log("In arguments: ", inArguments);
        $.each(inArguments, function (index, inArgument) {
            $.each(inArgument, function (key, val) {
                if (key === "message") {
                    message = val;
                }
            });
        });

        // If there is no message selected, disable the next button
        if (!message) {
            showStep(null, 1);
            connection.trigger("updateButton", { button: "next", enabled: false });
            // If there is a message, skip to the summary step
        } else {
            $("#select1")
                .find("option[value=" + message + "]")
                .attr("selected", "selected");
            $("#message").html(message);
            showStep(null, 3);
        }
    }

    // function requestSch (data) {
    //     // save schema
    //     console.log('Inside Save Method RequestedSchema');
    //     schema = data.schema;
    //     if (selectElement) {
    //         // Clear existing options
    //         selectElement.innerHTML = "";
    //
    //         // Add a default option
    //         const defaultOption = document.createElement("option");
    //         defaultOption.text = "Select a Field";
    //         defaultOption.value = "";
    //         selectElement.appendChild(defaultOption);
    //
    //         // Populate the dropdown with schema fields
    //         for (let i = 0; i < schema.length; i++) {
    //             const field = schema[i];
    //             const option = document.createElement("option");
    //             option.text = field.name; // Display the field name
    //             option.value = "{{" + field.key + "}}"; // Use the field key as the option value
    //             selectElement.appendChild(option);
    //         }
    //     } else {
    //         console.error("selectElement is not defined. Make sure the dropdown ID is correct and the code runs after the DOM is ready.");
    //     }
    //     console.log('*** Schema ***', JSON.stringify(data, null, 2));
    // }

    function requestSch(data) {
        console.log('Inside Save Method RequestedSchema');
        schema = data.schema;

        const container = document.createElement("div");
        container.id = "dynamicFieldsContainer";

        // Clean old content
        const existing = document.getElementById("dynamicFieldsContainer");
        if (existing) existing.remove();

        const form = document.createElement("form");
        form.id = "dynamicForm";

        schema.forEach((field) => {
            const label = document.createElement("label");
            label.innerText = field.name;
            label.style.display = "block";

            let inputElement;

            // 1. Dropdown for FirstName and CID
            if (["emailAddress", "CID", "FirstName"].includes(field.name)) {
                inputElement = document.createElement("select");
                inputElement.id = field.name;

                const defaultOption = document.createElement("option");
                defaultOption.value = "";
                defaultOption.text = `-- Select ${field.name} --`;
                inputElement.appendChild(defaultOption);

                const dynamicOption = document.createElement("option");
                dynamicOption.value = `{{${field.key}}}`;
                dynamicOption.text = field.name;
                inputElement.appendChild(dynamicOption);
            }

            // 2. Platform dropdown

            inputElement = document.createElement("select");
            inputElement.id = "Platform";

            ["", "android", "ios"].forEach((opt) => {
                const option = document.createElement("option");
                option.value = opt;
                option.text = opt === "" ? "-- Select Platform --" : opt;
                inputElement.appendChild(option);
            });

            // 3. message box (multiline with char limit and variable insertion)
            inputElement = document.createElement("textarea");
            inputElement.id = "message";
            inputElement.maxLength = 160;
            inputElement.rows = 4;
            inputElement.cols = 40;
            inputElement.placeholder = "Type your message here...";

            // 4. deeplink / imageurl - single line input

            inputElement = document.createElement("input");
            inputElement.type = "text";
            inputElement.id = "deeplink";
            inputElement.maxLength = field.length || 1000;
            inputElement.placeholder = `Enter deeplink`;

            inputElement = document.createElement("input");
            inputElement.type = "text";
            inputElement.id = "imageurl";
            inputElement.maxLength = field.length || 1000;
            inputElement.placeholder = `Enter imageurl`;


            if (inputElement) {
                inputElement.style.marginBottom = "10px";
                form.appendChild(label);
                form.appendChild(inputElement);
            }
        });

        container.appendChild(form);
        document.getElementById("step1").appendChild(container);
    }


    function requestTriggerEvent (eventDefinitionModel) {
        console.log('Inside RequestTriggerEvent');
        if (eventDefinitionModel) {
            console.log('eventDefinitionModel present');
            eventDefinitionKey = eventDefinitionModel.eventDefinitionKey;
            console.log('*** Event Definition Key ***', eventDefinitionKey);
        }
    }

    function onGetTokens(tokens) {
        // Response: tokens = { token: <legacy token>, fuel2token: <fuel api token> }
        // console.log(tokens);
    }

    function onGetEndpoints(endpoints) {
        // Response: endpoints = { restHost: <url> } i.e. "rest.s1.qa1.exacttarget.com"
        // console.log(endpoints);
    }

    function onClickedNext() {
        if (
            (currentStep.key === "step3" && steps[3].active === false) ||
            currentStep.key === "step4"
        ) {
            save();
        } else {
            connection.trigger("nextStep");
        }
    }

    function onClickedBack() {
        connection.trigger("prevStep");
    }

    function onGotoStep(step) {
        showStep(step);
        connection.trigger("ready");
    }

    function showStep(step, stepIndex) {
        if (stepIndex && !step) {
            step = steps[stepIndex - 1];
        }

        currentStep = step;

        $(".step").hide();

        switch (currentStep.key) {
            case "step1":
                $("#step1").show();
                connection.trigger("updateButton", {
                    button: "next",
                    enabled: Boolean(getMessage()),
                });
                connection.trigger("updateButton", {
                    button: "back",
                    visible: false,
                });
                break;
            case "step2":
                $("#step2").show();
                connection.trigger("updateButton", {
                    button: "back",
                    visible: true,
                });
                connection.trigger("updateButton", {
                    button: "next",
                    text: "next",
                    visible: true,
                });
                break;
            case "step3":
                $("#step3").show();
                connection.trigger("updateButton", {
                    button: "back",
                    visible: true,
                });
                if (lastStepEnabled) {
                    connection.trigger("updateButton", {
                        button: "next",
                        text: "next",
                        visible: true,
                    });
                } else {
                    connection.trigger("updateButton", {
                        button: "next",
                        text: "done",
                        visible: true,
                    });
                }
                break;
            case "step4":
                $("#step4").show();
                break;
        }
    }

    // function save() {
    //     console.log('Inside Save Method');
    //
    //     var name = $("#select1").find("option:selected").html();
    //     var value = getMessage();
    //
    //     // 'payload' is initialized on 'initActivity' above.
    //     // Journey Builder sends an initial payload with defaults
    //     // set by this activity's config.json file.  Any property
    //     // may be overridden as desired.
    //     payload.name = name;
    //
    //     //
    //     // const keyList = schema.map(item => `{{${item.key}}}`);
    //     // console.log("Extracted keys wrapped in double curly braces:");
    //     // console.log(keyList);
    //     var hasInArguments = Boolean(
    //         payload["arguments"] &&
    //         payload["arguments"].execute &&
    //         payload["arguments"].execute.inArguments &&
    //         payload["arguments"].execute.inArguments.length > 0
    //     );
    //     var inArguments = payload["arguments"].execute.inArguments;
    //     if(hasInArguments){
    //         payload["arguments"].execute.inArguments = [{ message: value}, ...inArguments];
    //     } else{
    //         payload["arguments"].execute.inArguments = [{ message: value}];
    //     }
    //
    //     payload["metaData"].isConfigured = true;
    //
    //     connection.trigger("updateActivity", payload);
    // }

    function save() {
        console.log('Inside Save Method');

        // Set activity name (optional, based on a field or static)
        payload.name = "Custom SMS Activity";

        // Build inArguments from all dynamic inputs
        const inArguments = [];

        const formElements = document.querySelectorAll("#dynamicForm input, #dynamicForm select, #dynamicForm textarea");

        formElements.forEach((element) => {
            const key = element.id;
            const value = element.value?.trim() ?? "";
            if (key && value !== "") {
                const arg = {};
                arg[key] = value;
                inArguments.push(arg);
            }
        });

        payload.arguments = payload.arguments || {};
        payload.arguments.execute = payload.arguments.execute || {};

        payload.arguments.execute.inArguments = inArguments;
        payload.metaData = payload.metaData || {};
        payload.metaData.isConfigured = true;

        console.log("Saving Payload:", JSON.stringify(payload, null, 2));

        connection.trigger("updateActivity", payload);
    }


    function getMessage() {
        return $("#select1").find("option:selected").attr("value").trim();
    }
});