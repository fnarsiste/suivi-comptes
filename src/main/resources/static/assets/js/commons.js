/**
Commons js file
*/

function hideSpinner() {
    $.MainApp.Hideloader();
}

function showSpinner(){
    $.MainApp.Showloader();
}

function encodeValue(value){
    encodeURIComponent(value);
}

function showAnimatedError(errorDiv, message) {
    console.log({errorDiv, message});
}

function appDeleteListeRow(fn, e){
    e.preventDefault();
    const data = $(fn).data();
    swal({
        icon: "warning",
        title: "Etes vous sûr ?",
        text: "Une fois supprimée, vous ne serez plus en mesure de ...",
        buttons: true, dangerMode: true, closeOnEsc: false,
        closeOnClickOutside: false
    })
    .then((response) => {
        if(!response) return;
        showSpinner();
        const targetUrl = `${data.href}${data.id}/supprimer`;
        $.ajax({
            type : "POST",
            url : targetUrl,
            data : JSON.stringify(data),
            dataType: 'json',
            contentType: 'application/json',
            success : function(response) {
                hideSpinner();
                if(!response.result) return alertMessageDanger(response.message);
                defaultAlertAction(response.message, function() {
                    document.location.reload();
                });
            },
            error : function() {
                hideSpinner();
            }
        });
    });
}

function msgBox(message) {
    defaultAlertAction(message);
}

function showSwallMessage(title, message, type, callback) {
    //"warning", "error", "success" and "info"
    console.log({title, message, type, callback});
    const $div = $('#js-temp-message');
    icon = type || 'info';
    title = title || 'Suivi Comptes';
    message = message || "<b>Aucun message n'est defini</b>";
    $div.html(message);
    const settings = {title, icon, text: message, content: $div, closeOnClickOutside: false};
    swal(settings).then((value) => {
        if(callback) callback();
    });
}

function defaultAlertAction(message, callback) {
    showSwallMessage(null,message, null, callback);
    return true;
}

function alertMessageDanger(message, callback) {
    showSwallMessage(null, message, 'error', callback);
    return true;
}

function getEltSelector(elt, defaultPrefix) {
	if (emptyValue(elt)) return '';
	else if (!isString(elt)) return elt;
	else {
		if (!elt.startsWith('.') && !elt.startsWith('#')) {
			defaultPrefix = !emptyValue(defaultPrefix) ? defaultPrefix : '#';
			return defaultPrefix+elt;
		} else return elt;
	}
}

function getAjaxResponse(response, defaultResp) {
	response = $.trim(response);
    return (!emptyValue(response)) ? JSON.parse(response) : defaultResp;
}

function emptyValue(value) {
	return (value == 'undefined')
		|| (value == undefined)
		|| (value == 'null')
		|| (value == null)
		|| (value == '');
}

function emptyValueAll(...values) {
	let res = true;
	for (var i=0; i<values.length; i++) {
		res = !emptyValue(values[i]);
		if (!res) break;
	}
	return res;
}

function emptyValueOne(...values) {
	let res = false;
	for (var i=0; i<values.length; i++) {
		res = emptyValue(values[i]);
        if (res) break;
	}
	return res;
}

function appendSubmitFormDataValue(fData, fDataStr, key, value) {
	if (emptyValue(fData)) 	 fData = new FormData();
	if (emptyValue(fDataStr)) fDataStr = '';
	if (isString(value)) value = encodeURIComponent(value);

	const fDataStrSeparator = '&';
	const fDataValSeparator = ',';
	const fDataValEqual = '=';

	if (fData.has(key)) {
		if (!emptyValue(value)) {
			let fDataArray = fDataStr.split(fDataStrSeparator);
			let fDataArrayValue;
			let fDataValuesArray;

			for (var i = 0; i < fDataArray.length; i++) {
				fDataArrayValue = fDataArray[i];
				if (fDataArrayValue.startsWith(key + fDataValEqual)) {
					fDataValuesArray = fDataArrayValue.split(fDataValEqual)[1].split(fDataValSeparator);
					if (!fDataValuesArray.includes(value)) fDataArray[i] += fDataValSeparator + value;
					break;
				}
			}
			fDataStr = fDataArray.join(fDataStrSeparator);
		}
	} else {
		if (fDataStr != '') fDataStr += fDataStrSeparator;
		fDataStr += key + '=' + value;
	}
	fData.append(key, value); //ajoute la clé même si elle existe déjà

	return {fData, fDataStr};
}

function doSubmitForm(form, event) {
	let $form = $(getEltSelector(form));
	let formData = new FormData($form[0]);
	let fData = new FormData();
	let fDataStr = '';
	let appendRes;

	// ajoute les champs du formulaire
	for (let entry of formData.entries()) {
		appendRes = appendSubmitFormDataValue(fData, fDataStr, entry[0], entry[1]);
		fData = appendRes['fData'];
		fDataStr = appendRes['fDataStr'];
	}

	// cas particulier : ajoute les champs des DataTable
	$form.find('table.dataTable').each(function () {
        let dtInstance = $(this).DataTable();
        let dtData = dtInstance.rows().data();

        dtData.each(function(data, index){
            let $inputs = $(dtInstance.row(index).node()).find('[name]').not(':disabled');

            $inputs.each(function () {
            	if ($(this).is(':checkbox') || $(this).is(':radio')) {
	            	if ($(this).is(':checked')) {
	            		appendRes = appendSubmitFormDataValue(fData, fDataStr, $(this).attr('name'), $(this).val());
						fData = appendRes['fData'];
						fDataStr = appendRes['fDataStr'];
	            	}
            	}
            	else {
            		appendRes = appendSubmitFormDataValue(fData, fDataStr, $(this).attr('name'), $(this).val());
					fData = appendRes['fData'];
					fDataStr = appendRes['fDataStr'];
            	}
            });
        });
	});

    showSpinner();
	let formAction = $form.attr('action');
	onSubmit(!emptyValue(formAction) ? formAction : location.href, fDataStr);

	event.preventDefault();
	return false;
}

function onSubmit(targetUrl, formData, options) {
	let defaultOptions = {
        successCallback: null,
        errorCallback: null,
        errorDiv: 'frm_error'
     };

    options = $.extend({}, defaultOptions, options);
	$.ajax({
        type : "POST",
        url : targetUrl,
        data : formData,
        // dataType : 'text', contentType: false,
        contentType: 'application/x-www-form-urlencoded;charset=ISO 8859-1',
        processData: false,
        success : function(response) {
            hideSpinner();
            response = $.trim(response);
            response = (response != '') ? JSON.parse(response) : {"result":"error", "type":"EmptyResponse", "message":"Aucune réponse"};
            if (response.result == 'error') {
                if ($(getEltSelector(options.errorDiv)).length > 0) {
                    showAnimatedError(options.errorDiv, response.message);
                } else {
                    defaultAlertAction(response.message);
                }
            } else {
                if (!emptyValue(options.successCallback)) {
                    options.successCallback(response);
                } else {
                    showOperationEndedMsg(response.msgType, response.endPage, {handle: response.handle, remaining: response.remaining});
                }
            }
        },
        error : function() {
            hideSpinner();
            if (!emptyValue(options.errorCallback)) options.errorCallback();
            else defaultAlertAction("<span style='color:red'>Une erreur est survenue lors de l'opération</span>");
        }
    });
}

function showOperationEndedMsg(msgType, endPage, options) {
    if(emptyValue(msgType)) return;
	let defaultOptions = {handle: "", remaining: ""};
    options = $.extend({}, defaultOptions, options);
    let msg = "Opération bien effectuée";
    switch (msgType) {
        case 'PROCESS_END':
            fermerPopUp('alert_box_traitement');
            alert_message_exercice_process_end("Traitement terminé", endPage);
            break;
        case 'EXERCICE':
            alert_message_exercice(msg, endPage);
            break;
        case 'LOADFILE':
            let msgRemaining = '';
            let msgHandle = '';
            let totalFile = parseInt(options.handle) + parseInt(options.remaining);

            msgHandle = '<div style="padding:10px"><li><p style="color:#0000FF;">Nombre de lignes traitées : ' + handle + ' / ' + totalFile + '</p></li>';

            if (parseInt(remaining) != 0) {
                msgRemaining = '<li><p style="color:#FF3300">Nombre de lignes non traitées : ' + remaining + ' / ' + totalFile + '</p></li></div>';
            } else msgRemaining = '</div>';
            alert_message_exercice('<h4"><b>' + msg + '</h4></b>.<br/>' + msgHandle + msgRemaining, endPage);
            break;
        default :
            switch (msgType) {
                case 'CREATE_SUCCES'			: msg = "Enregistrement bien effectué."; 				break;
                case 'UPDATE_SUCCES'			: msg = "Modification bien effectuée."; 					break;
                case 'DELETE_SUCCES'			: msg = "Suppression bien effectuée."; 					break;
                case 'FORWARD_SUCCES'			: msg = "Transmission bien effectuée."; 					break;
                case 'VALIDATION_SUCCES'		: msg = "Validation bien effectuée."; 					break;
                case 'VALIDATION_RESERVE_SUCCES': msg = "Validation sous réserve bien effectuée.";break;
                case 'RETOUR_SUCCES'			: msg = "Retour bien effectué."; 						break;
                case 'ANNULATION_SUCCES'		: msg = "Annulation bien effectuée."; 					break;
                case 'RECTIFICATION_SUCCES'		: msg = "Rectification bien effectuée."; 				break;
                case 'DIFFERER_SUCCES'			: msg = "Différé bien effectué."; 			break;
                case 'REJET_SUCCES'				: msg = "Rejet bien effectué."; 							break;
                case 'AFFECTATION_SUCCES'		: msg = "Affectation bien effectuée."; 					break;
                case 'RETRAIT_SUCCES'			: msg = "Retrait bien effectué."; 						break;
                case 'CONSOLIDATION_SUCCES'		: msg = "Consolidation bien effectuée."; 				break;
                case 'SUCCES'					: msg = "Opération bien effectuée"; 				break;
            }
            defaultAlertAction(msg, () => {if (endPage != '') document.location.href = endPage});
        break
    }
}

function appExit(fn, e) {
    e.preventDefault();
    const {contextpath} = $(fn).data();
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
}

$(document).ready(function(){
   // Initalize data table with buttons
   const table = $('#datatable-buttons').DataTable({
      lengthChange: !true,
      buttons: ['copy', 'excel', 'pdf'], //, 'colvis'],
      language: LANG,
      order: [[TABLE_SORTING, 'asc']]
   });
   table.buttons().container().appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');

   $('.js-button-logout').on('click', function(e) {appExit(this, e);});

    // Tout formulaire d'enregistrement et de modification de données
   $('body').on('submit', '#form, #'+$(document.myform).attr('id'), function (e) {doSubmitForm(this, e);});

   // Ecoute sur les boutons de suppression sur les listes de données
   $('.js-app-button-delete').on('click', function (e) {appDeleteListeRow(this, e)});
    // Convertir en maj les champs tagués de 'js-to-upper'
   $('.js-to-upper').on('change', function (){$(this).val($(this).val().toUpperCase());});

   $('.select2').select2({width: '100%'})

   // Toutes les pages qui aurient définies cette fonction
    if(typeof runOnceAllLoaded === 'function') runOnceAllLoaded();
});