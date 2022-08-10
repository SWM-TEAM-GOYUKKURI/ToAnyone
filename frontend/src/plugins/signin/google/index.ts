const gsiScript = document.createElement("script");
gsiScript.src = "https://accounts.google.com/gsi/client";
gsiScript.async = true;
gsiScript.defer = true;

document.body.appendChild(gsiScript);
