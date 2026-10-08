/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          50: '#e8f6f0',
          100: '#d1edd1',
          500: '#0e7a53',
          600: '#0a583c',
          700: '#063f2b'
        },
        flame: {
          500: '#ff7a00',
          600: '#ea580c'
        },
        macro: {
          protein: '#e11d48',
          carbs: '#d97706',
          fat: '#0891b2'
        }
      },
      fontFamily: {
        sans: ['"Hind Siliguri"', 'system-ui', 'sans-serif']
      }
    },
  },
  plugins: [],
}
