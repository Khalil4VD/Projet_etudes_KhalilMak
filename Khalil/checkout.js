const appearance = {
    theme: 'night',
    variables: {
      fontFamily: 'Sohne, system-ui, sans-serif',
      fontWeightNormal: '500',
      borderRadius: '8px',
      colorBackground: '#0A2540',
      colorPrimary: '#EFC078',
      accessibleColorOnColorPrimary: '#1A1B25',
      colorText: 'white',
      colorTextSecondary: 'white',
      colorTextPlaceholder: '#727F96',
      tabIconColor: 'white',
      logoColor: 'dark'
    },
    rules: {
      '.Input, .Block': {
        backgroundColor: 'transparent',
        border: '1.5px solid var(--colorPrimary)'
      }
    }
  };
  
  const elements = stripe.elements({clientSecret, appearance});