function onLogin(xhr, status, args) {
    if (!args.validationFailed && args.login) {
      setTimeout(function() {
        window.location = args.target;
      }, 500);
    }
  }