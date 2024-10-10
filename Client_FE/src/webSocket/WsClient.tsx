import { Client } from "@stomp/stompjs";

export class SocketClient {
  url: string;
  jwt: string;
  client: Client;

  constructor(url: string, jwt: string) {
    this.url = url;
    this.jwt = jwt;
    this.client = new Client();

    this.client.configure({
      brokerURL: url,
      connectHeaders: {
        Authorization: `Bearer ${jwt}`,
      },
      onConnect: () => {
        console.log("connected to WS!");
      },
    });

    this.client.activate;
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

  awaitConnection = async (awaitConnectionConfig: {
    retries: number;
    curr: number;
    timeinterval: number;
  }) => {
    const {
      retries = 3,
      curr = 0,
      timeinterval = 100,
    } = awaitConnectionConfig || {};
    return new Promise((resolve, reject) => {
      console.log(timeinterval);
      setTimeout(() => {
        if (this.connected) {
          resolve(this.connected);
        } else {
          console.log(`Failed To connect retrying for the ${curr + 1}`);
          if (curr >= retries) {
            console.log("Failed to connect :(");
            reject();
          }
        }
      });
    });
  };
}
