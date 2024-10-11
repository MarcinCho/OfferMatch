import { Conversation, Avatar } from "@chatscope/chat-ui-kit-react";

export const ConvPanel = () => {
  return (
    <Conversation
      info="Yes i can do it for you"
      lastSenderName="Akane"
      name="Akane"
    >
      <Avatar
        name="Akane"
        src="https://chatscope.io/storybook/react/assets/akane-MXhWvx63.svg"
        status="eager"
      />
    </Conversation>
  );
};
