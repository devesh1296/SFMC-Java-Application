define(["postmonger"], function (Postmonger) {
    "use strict";

    var connection = new Postmonger.Session();
    var payload = {};
    var schema;
    document.getElementById("select1");

    $(window).ready(onRender);

    connection.on("initActivity", initialize);
    connection.on('requestedSchema', requestSch);
    connection.on("requestedTokens", onGetTokens);
    connection.on("requestedEndpoints", onGetEndpoints);

    connection.on("clickedNext", save);

    function onRender() {
        // JB will respond the first time 'ready' is called with 'initActivity'
        connection.trigger("ready");

        connection.trigger('requestSchema');
        connection.trigger("requestTokens");
        connection.trigger("requestEndpoints");
        connection.trigger("updateButton", { button: "next", enabled: true });
    }

    function initialize(data) {
        if (data) {
            payload = data;
        }
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
    }

    function requestSch(data) {
        console.log('Inside Save Method RequestedSchema');
        schema = data.schema;

        if (!document.getElementById("dynamicFormStyle")) {
            const style = document.createElement("style");
            style.id = "dynamicFormStyle";
            style.innerHTML = `
          #dynamicForm {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f9f9fb;
            padding: 24px 32px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.07);
            max-width: 480px;
            margin: 24px auto;
          }
          #dynamicForm label {
            font-weight: 600;
            margin-bottom: 6px;
            color: #333;
            display: block;
          }
          #dynamicForm input,
          #dynamicForm select,
          #dynamicForm textarea {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 18px;
            border: 1px solid #d1d5db;
            border-radius: 6px;
            font-size: 15px;
            background: #fff;
            box-sizing: border-box;
            transition: border-color 0.2s;
          }
          #dynamicForm input:focus,
          #dynamicForm select:focus,
          #dynamicForm textarea:focus {
            border-color: #6366f1;
            outline: none;
          }
          #dynamicForm textarea {
            resize: vertical;
          }
        `;
            document.head.appendChild(style);
        }

        const container = document.createElement("div");
        container.id = "dynamicFieldsContainer";

        // Clean old content
        const existing = document.getElementById("dynamicFieldsContainer");
        if (existing) existing.remove();

        const form = document.createElement("form");
        form.id = "dynamicForm";

        const platformLabel = document.createElement("label");
        platformLabel.innerText = "Platform";
        platformLabel.style.display = "block";

        const platformSelect = document.createElement("select");
        platformSelect.id = "Platform";
        ["", "ANDROID", "IOS", "WEB", "ALL"].forEach((opt) => {
            const option = document.createElement("option");
            option.value = opt;
            option.text = opt === "" ? "-- Select Platform --" : opt;
            platformSelect.appendChild(option);
        });

        form.appendChild(platformLabel);
        form.appendChild(platformSelect);

        ["Title", "Url", "Image Url"].forEach((fieldName) => {
            const label = document.createElement("label");
            label.innerText = fieldName;
            label.style.display = "block";

            const input = document.createElement("input");
            input.type = "text";
            input.id = fieldName;
            input.placeholder = `Enter ${fieldName}`;
            input.style.marginBottom = "10px";

            form.appendChild(label);
            form.appendChild(input);
        });

        // 3. Always add message box with Hi {{FirstName}} prefilled if found in schema
        const messageLabel = document.createElement("label");
        messageLabel.innerText = "Message";
        messageLabel.style.display = "block";

        const messageArea = document.createElement("textarea");
        messageArea.id = "message";
        messageArea.rows = 4;
        messageArea.cols = 40;
        messageArea.maxLength = 500;
        messageArea.placeholder = "Type your message here...";

        form.appendChild(messageLabel);
        form.appendChild(messageArea);


        container.appendChild(form);
        document.getElementById("step1").appendChild(container);
    }

    function onGetTokens(tokens) {
        // Response: tokens = { token: <legacy token>, fuel2token: <fuel api token> }
        // console.log(tokens);
    }

    function onGetEndpoints(endpoints) {
        // Response: endpoints = { restHost: <url> } i.e. "rest.s1.qa1.exacttarget.com"
        // console.log(endpoints);
    }

    function save() {
        console.log('Inside Save Method');
        console.log('Schema:', schema);

        payload.name = "Custom RDNC Activity";

        const inArguments = [];

        const formElements = document.querySelectorAll("#dynamicForm input, #dynamicForm select, #dynamicForm textarea");

        formElements.forEach((element) => {
            const key = element.id;
            const value = element.value?.trim() ?? "";

            if (key && value) {
                const arg = {};
                arg[key] = value;
                inArguments.push(arg);
            }
        });
        schema.forEach((field) => {
            const key = field.name;
            const value = `{{${field.key}}}`;
            if (key && value) {
                const arg = {};
                arg[key] = value;
                inArguments.push(arg);
            }
        })

        payload.arguments = payload.arguments || {};
        payload.arguments.execute = payload.arguments.execute || {};
        payload.arguments.execute.inArguments = inArguments;


        payload.metaData = payload.metaData || {};
        payload.metaData.isConfigured = true;

        console.log("Final Payload:", JSON.stringify(payload, null, 2));
        connection.trigger("updateActivity", payload);
    }
});