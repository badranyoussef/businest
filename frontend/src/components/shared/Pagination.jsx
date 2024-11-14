import PropTypes from 'prop-types';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import '../../styles/Pagination.css';

export function Pagination({ currentPage, totalPages, onPageChange }) {
  return (
    <div className="pagination">
      <button
        className="pagination-button"
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1}
      >
        <ChevronLeft size={16} />
      </button>

      {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
        <button
          key={page}
          className={`pagination-button ${currentPage === page ? 'active' : ''}`}
          onClick={() => onPageChange(page)}
        >
          {page}
        </button>
      ))}

      <button
        className="pagination-button"
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages}
      >
        <ChevronRight size={16} />
      </button>
    </div>
  );
}

// Prop validation using PropTypes
Pagination.propTypes = {
  currentPage: PropTypes.number.isRequired,  // Ensures currentPage is a number and required
  totalPages: PropTypes.number.isRequired,   // Ensures totalPages is a number and required
  onPageChange: PropTypes.func.isRequired,   // Ensures onPageChange is a function and required
};
