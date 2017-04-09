var http = require('http');

exports.handler = function(event, context, callback) {
    // TODO implement
    try {
        if (event.session.new) {
            console.log('Hello from Lambda');
        }

        switch (event.request.type) {
            case "LaunchRequest":

                console.log("Launch Request");
                break;
            case "IntentRequest":
                console.log("Intent Req from intent name:" + event.request.intent.name);

                console.log("Intent: " + JSON.stringify(event.request.intent));

                var vaadinIntent = event.request.intent;

                console.log("Element: " + vaadinIntent.slots.element.value);

                var data = JSON.stringify({

                    "uid": 123456,
                    "isNewPage": true,
                    template: "login",
                    elementName: vaadinIntent.slots.element.value,
                    elementAttributes: {
                        type: "button",
                        name: "btn",
                        id: "btn",
                        postion: "center",
                        color: vaadinIntent.slots.Color.value,
                        value: "Save"

                    }
                });

                var options = {
                    host: 'ec2-52-91-84-25.compute-1.amazonaws.com',
                    port: '3000',
                    path: '/test',
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8',
                        'Content-Length': data.length
                    }
                };



                callback = function(response) {
                    console.log('In callback!');
                    var str = '';
                    response.on('data', function(chunk) {
                        str += chunk;
                        console.log(chunk);
                    });

                    response.on('end', function() {
                        console.log('Final String' + str);
                        context.succeed(
                            generateResponse({},
                                buildSpeechletResponse("Hello World From Alexa", true)
                            )
                        );
                    });

                };

                var postReq = http.request(options, callback);

                postReq.write(data);
                postReq.end();
                break;
            case "SessionEndedRequest":
                console.log("Session Ended Request");
                break;
            default:
                context.fail("Invalid request type: $(event.request.type)");
        }
    } catch (error) {
        context.fail('Exception Occured' + error);
    }
};

var buildSpeechletResponse = function(outputText, shouldEndSession) {

    return {
        "outputSpeech": {
            "type": "PlainText",
            "text": outputText
        },
        "shouldEndSession": shouldEndSession
    }
};

var generateResponse = function(sessionAttributes, speechletResponse) {
    return {
        "version": "1.0",
        "sessionAttributes": sessionAttributes,
        "response": speechletResponse
    }
};
