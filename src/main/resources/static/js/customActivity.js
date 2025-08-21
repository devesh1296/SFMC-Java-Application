define(["postmonger"], function (Postmonger) {
    "use strict";

    var connection = new Postmonger.Session();
    var payload = {};
    var schema;
    document.getElementById("select1");

    $(window).ready(onRender);

    connection.on("initActivity", initialize);
    connection.on('requestedSchema', requestSch);
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

        style.innerHTML += `
  .platform-box {
    border: 1px solid #d1d5db;
    border-radius: 6px;
    background: #fff;
    padding: 16px;
    margin-bottom: 18px;
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  .platform-box label {
    font-weight: 400;
    margin-bottom: 0;
    color: #333;
    display: flex;
    align-items: center;
    gap: 8px;
  }
`;
        const platformLabel = document.createElement("label");
        platformLabel.innerText = "Platform";
        platformLabel.style.display = "block";

        const platforms = ["ANDROID", "IOS", "WEB", "ALL"];
        const platformContainer = document.createElement("div");
        platformContainer.id = "PlatformContainer";
        platformContainer.className = "platform-box";

        platforms.forEach((opt) => {
            const label = document.createElement("label");
            label.htmlFor = `Platform_${opt}`;

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.name = "Platform";
            checkbox.value = opt;
            checkbox.id = `Platform_${opt}`;

            label.appendChild(checkbox);
            label.appendChild(document.createTextNode(opt));
            platformContainer.appendChild(label);
        });

        form.appendChild(platformLabel);
        form.appendChild(platformContainer);

        const tenantIdLabel = document.createElement("label");
        tenantIdLabel.innerText = "Tenant Id";
        tenantIdLabel.style.display = "block";

        const tenantIdInput = document.createElement("input");
        tenantIdInput.type = "text";
        tenantIdInput.id = "Tenant Id";
        tenantIdInput.value = "qxjed8";
        tenantIdInput.readOnly = true;
        tenantIdInput.style.background = "#f3f4f6";

        form.appendChild(tenantIdLabel);
        form.appendChild(tenantIdInput);

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
        messageArea.placeholder = "Type your message here...";

        form.appendChild(messageLabel);
        form.appendChild(messageArea);


        container.appendChild(form);
        document.getElementById("step1").appendChild(container);
    }

    function save() {
        console.log('Inside Save Method');
        console.log('Schema:', schema);

        payload.name = "Custom RDNC Activity";

        const inArguments = [];
        // Collect platform values
        const checkedPlatforms = Array.from(document.querySelectorAll('#PlatformContainer input[type="checkbox"]:checked'))
            .map(cb => cb.value);
        if (checkedPlatforms.length > 0) {
            inArguments.push({ Platform: checkedPlatforms });
        }

        const formElements = document.querySelectorAll("#dynamicForm input:not([type='checkbox']), #dynamicForm select, #dynamicForm textarea");

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
