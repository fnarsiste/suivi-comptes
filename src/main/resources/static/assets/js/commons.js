
$(document).ready(function(){
   // Initalize data table with buttons
   const table = $('#datatable-buttons').DataTable({
      lengthChange: !true,
      buttons: ['copy', 'excel', 'pdf'], //, 'colvis'],
      language: LANG
   });
   table.buttons().container().appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');

   $('.js-button-logout').on('click', function(e) {
      e.preventDefault();
      const {contextpath} = $(this).data();
      swal({
         title: "Suivi Compte!",
         text: `Voulez-vous réellement quitter l'application ?`,
         icon: "warning",
         buttons: ['Continuez', 'Quittez'],
         dangerMode: true,
      }).then((canExit) => {
         if (canExit) {
            document.location.href = contextpath;
         }
      });
   });
   if(typeof runOnceAllLoaded === 'function') runOnceAllLoaded();
});