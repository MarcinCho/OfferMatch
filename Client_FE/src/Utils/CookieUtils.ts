const setCookie = (name: string, value: string, days: number) => {
  let expires = "";
  if (days) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + (value || "") + expires + "; path=/";
};

const getCookie = (name: string) => {
  const cookie = document.cookie
    .split(";")
    .find((c) => c.trim().startsWith(`${name}=`));
  return cookie ? cookie.split("=")[1] : null;
};

const ereseCookie = (username: string) => {
  document.cookie =
    username + "=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
};

export { setCookie, getCookie, ereseCookie };
