import { Search } from 'lucide-react';
import PropTypes from 'prop-types';
import '../../styles/SearchBar.css';

export function SearchBar({ value, onChange, placeholder = 'Search...' }) {
  return (
    <div className="search-container">
      <Search className="search-icon" size={18} />
      <input
        type="text"
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={placeholder}
        className="search-input"
      />
    </div>
  );
}

// PropTypes validation
SearchBar.propTypes = {
  value: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  placeholder: PropTypes.string,
};

export default SearchBar;
