import React from "react";
import { Button } from "primereact/button";
import { Toolbar } from "primereact/toolbar";
import { Terminal } from "primereact/terminal";
import { TerminalService } from "primereact/terminalservice";

export const HomePage = () => {
  return (
    <div>
      <Toolbar />
      <Button className="bg-indigo-400">Does it works</Button>
      <Terminal
        welcomeMessage="Welcome to PrimeReact"
        prompt="primereact $"
        pt={{
          root: "bg-gray-900 text-white border-round",
          prompt: "text-gray-400 mr-2",
          command: "text-primary-300",
          response: "text-primary-300",
        }}
      />
    </div>
  );
};
