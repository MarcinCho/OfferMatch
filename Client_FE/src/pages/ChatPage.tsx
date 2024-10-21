// UI => https://chatscope.io/storybook/react/?path=/docs/documentation-introduction--docs

import { MainContainer, Message } from "@chatscope/chat-ui-kit-react";
import { ContactsPanel } from "../components/Chat/ContactsPanel";
import { ConvPanel } from "../components/Chat/ConvPanel";
import { ChatWindow } from "../components/Chat/ChatWindow";
import "@chatscope/chat-ui-kit-styles/dist/default/styles.min.css";

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
    <div></div>
  );
};
