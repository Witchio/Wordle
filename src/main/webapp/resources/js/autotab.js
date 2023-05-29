//Automatically tab when input is filled && focus element
//Restrict to letter & number
document.addEventListener("DOMContentLoaded", function () {
  var inputs = Array.prototype.slice.call(
    document.getElementsByClassName("letterInput")
  );
  var submitButton = document.getElementById("letterForm:submitBtn");
  inputs.forEach(function (input, i) {
    input.addEventListener("input", function (event) {
      var inputKey = event.data;
      var regex = /^[a-z0-9]+$/i;
      console.log("event", event);
      if (regex.test(inputKey)) {
        if (this.value.length === this.maxLength && i < inputs.length - 1) {
          inputs[i + 1].focus();
          inputs[i + 1].select();
        } else if (i === inputs.length - 1) {
          submitButton.focus();
        }
      }
    });
  });
});
