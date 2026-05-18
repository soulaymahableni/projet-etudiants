describe('Gestion des etudiants', () => {
  it('affiche la liste des etudiants', () => {
    cy.visit('/etudiants');
    cy.get('[data-testid="etudiant-list"]').should('be.visible');
  });

  it('navigue vers la creation', () => {
    cy.visit('/etudiants');
    cy.contains('+ Nouveau').click();
    cy.url().should('include', '/etudiants/nouveau');
  });

  it('cree un nouvel etudiant', () => {
    const cin = `TEST${Date.now()}`;
    cy.visit('/etudiants/nouveau');
    cy.get('input[name="cin"]').type(cin);
    cy.get('input[name="nom"]').type('Cypress User');
    cy.get('input[type="date"]').type('2000-01-01');
    cy.get('button[type="submit"]').click();
    cy.url().should('include', '/etudiants');
    cy.contains('Cypress User');
  });
});
