// UI => https://chatscope.io/storybook/react/?path=/docs/documentation-introduction--docs

import { MainContainer, Message } from "@chatscope/chat-ui-kit-react";
import React, { useContext, useState } from "react";
import { ContactsPanel } from "../components/Chat/ContactsPanel";
import { ConvPanel } from "../components/Chat/ConvPanel";
import { ChatWindow } from "../components/Chat/ChatWindow";
import "@chatscope/chat-ui-kit-styles/dist/default/styles.min.css";
import { SidePanel } from "../components/Chat/SidePanel";
import UserContext from "../webSocket/context/UserContext";
import SocketClientContext from "../webSocket/context/SocketClientContext";

const getMessages = () => {
  return (
    <>
      <Message
        model={{
          message: "Hello my friend",
          sentTime: "just now",
          direction: 0,
          position: 2,
          sender: "Joe",
        }}
      ></Message>
    </>
  );
};

export const ChatPage = () => {
  return !document.cookie.includes("Baerer") ? (
    <div className="p-5">
      <MainContainer
        responsive
        style={{
          height: "800px",
        }}
      >
        <ContactsPanel>
          <ConvPanel />
        </ContactsPanel>
        <ChatWindow children={getMessages()}></ChatWindow>
        {/* <SidePanel /> */}
      </MainContainer>
    </div>
  ) : (
    alert("Login to use chat")
  );
};
