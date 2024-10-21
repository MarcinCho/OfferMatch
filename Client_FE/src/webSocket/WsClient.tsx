import { Client } from "@stomp/stompjs";

export class SocketClient {
  url: string;
  jwt: string;
  client: Client;

  constructor(url: string, jwt: string) {
    this.url = url;
    this.jwt = jwt;
    this.client = new Client({
      brokerURL: url,
      connectHeaders: {
        Authorization: `Bearer ${jwt}`,
      },
      debug: function (str) {
        console.log(str);
      },
      reconnectDelay: 20000,
      heartbeatIncoming: 0,
      heartbeatOutgoing: 20000,
    });
    if (this.jwt) {
      this.client.activate();
    }

    this.client.onConnect = (frame) => {
      console.log("We are connected." + frame);
    };
  }

  get connected() {
    return this.client.connected;
  }

  publish = ({ destination, body }: { destination: string; body: string }) => {
    this.client.publish({
      destination: destination,
      body: JSON.stringify(body),
    });
  };

  deactivate = () => {
    this.client.deactivate();
  };

  subscribe = (
    topic: string,
    callback: (message: { body: string }) => void,
    ...forMessageTypes: string[]
  ) => {
    return this.client.subscribe(topic, (message) => {
      if (
        !forMessageTypes ||
        forMessageTypes.includes(JSON.parse(message.body).messageType)
      ) {
        callback(message);
      }
    });
  };

  awaitConnection = async (awaitConnectionConfig?: {
    retries: number;
    curr: number;
    timeinterval: number;
  }) => {
    const {
      retries = 3,
      curr = 0,
      // timeinterval = 100,
    } = awaitConnectionConfig || {};
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (this.connected) {
          console.log("We are connected to ws");
          resolve(this.connected);
        } else {
          for (let i = 0; i <= retries; i++) {
            console.log(`Failed To connect retrying for the ${curr + 1}`);
            if (curr >= retries) {
              console.log("Failed to connect :(");
              reject();
            }
          }
        }
      });
    });
  };
}
