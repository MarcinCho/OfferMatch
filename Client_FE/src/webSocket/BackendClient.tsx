import { BASE_URL_WS, LOGIN_URL_WS } from "../ConfigConstats";
import { IChatMessage } from "../models/IChatMessage";
import { IUserConnection } from "../models/IUserConnection";

class BackendClient {
  jwt: string;

  constructor() {
    this.jwt = "";
  }

  async sendRequest(
    url: string,
    reqConf?: {
      method: string;
      body?: string;
      headers: HeadersInit;
    }
  ) {
    if (!reqConf) {
      if (this.jwt) {
        console.log("No config but with token.");
        reqConf = {
          method: "GET",
          headers: {
            "content-type": "application/json",
            Authorization: `Bearer ${this.jwt}`,
          },
        };
      } else {
        reqConf = {
          method: "GET",
          headers: { "content-type": "application/json" },
        };
      }
    }

    console.log(reqConf);
    console.log(`here should be a token = ${this.jwt}`);
    const response = await fetch(url, reqConf);
    if (!response.ok) {
      throw new Error(`Http error! status: ${response.status}`);
    }

    return response.json();
  }

  async getFriends(): Promise<IUserConnection[]> {
    return this.sendRequest(`${BASE_URL_WS}/api/conv/contacts`);
  }

  login = async (loginRequestPayload: {
    username: string;
    password: string;
  }) => {
    console.log(loginRequestPayload);
    return this.sendRequest(LOGIN_URL_WS, {
      method: "POST",
      body: JSON.stringify(loginRequestPayload),
      headers: {
        "content-type": "application/json",
      },
    });
  };

  getUnseenMessages = async (fromUserId?: number) => {
    let url = `${BASE_URL_WS}/api/conv/unseenMessages`;
    if (fromUserId) {
      url = url + `/${fromUserId}`;
    }
    return this.sendRequest(url);
  };

  setReadMessages = async (chatMessages: IChatMessage) => {
    return this.sendRequest(`${BASE_URL_WS}/api/conversation/setReadMessages`, {
      method: "PUT",
      body: JSON.stringify(chatMessages),
      headers: {
        "content-type": "application/json",
      },
    });
  };
}
const backendClient = new BackendClient();

export default backendClient;
