import PropTypes from "prop-types";
import "./Button.css"; // Optional, if you want to style the button

export const Button = ({ text, action, type }) => {
  const buttonClass =
    type ? "positive-button" : "negative-button";

  return (
    <button className={`custom-button ${buttonClass}`} onClick={action}>
      {text}
    </button>
  );
};

Button.propTypes = {
  text: PropTypes.string.isRequired,
  action: PropTypes.func.isRequired,
    type: PropTypes.bool.isRequired,
};
