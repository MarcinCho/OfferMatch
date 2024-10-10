import { BASE_URL_WS, LOGIN_URL_WS } from "../ConfigConstats";
import { IChatMessage } from "../models/IChatMessage";

export class BackendClient {
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
      reqConf = { method: "GET", headers: {} };
    }
    const headers = new Headers(reqConf.headers);
    headers.append("content-type", "application/json");
    headers.append("Authorization", `Bearer ${this.jwt}`);
    reqConf.headers = headers;

    const response = await fetch(url, reqConf);
    return response.json;
  }

  async getFriends() {
    return this.sendRequest(`${BASE_URL_WS}/api/conversation/friends`);
  }

  login = async (loginRequestPayload: {
    username: string;
    password: string;
  }) => {
    return this.sendRequest(LOGIN_URL_WS, {
      method: "POST",
      body: JSON.stringify(loginRequestPayload),
      headers: {
        "content-type": "application/json",
      },
    });
  };

  getUnseenMessages = async (fromUserId: number) => {
    let url = `${BASE_URL_WS}/api/conversation/unseenMessages`;
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
